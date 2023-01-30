package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.constant.EntityName;
import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.constant.StatusConstant;
import com.example.dentalclinicmanagementsystem.dto.*;
import com.example.dentalclinicmanagementsystem.entity.*;
import com.example.dentalclinicmanagementsystem.exception.AccessDenyException;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.mapper.MaterialExportMapper;
import com.example.dentalclinicmanagementsystem.mapper.PatientRecordMapper;
import com.example.dentalclinicmanagementsystem.mapper.SpecimenMapper;
import com.example.dentalclinicmanagementsystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class PatientRecordService extends AbstractService {

    private final PatientRecordRepository patientRecordRepository;

    private final PatientRecordMapper patientRecordMapper;

    private final PatientRecordServiceMapRepository patientRecordServiceMapRepository;

    private final PatientRepository patientRepository;

    private final TreatmentRepository treatmentRepository;

    private final TreatmentServiceMapRepository treatmentServiceMapRepository;

    private final MaterialExportRepository materialExportRepository;

    private final MaterialRepository materialRepository;

    private final ServiceRepository serviceRepository;

    private final SpecimenRepository specimenRepository;

    private final WaitingRoomRepository waitingRoomRepository;

    private final LaboRepository laboRepository;

    private final MaterialExportMapper materialExportMapper;

    private final SpecimenMapper specimenMapper;

    private final NotifyRepository notifyRepository;

    private final ReceiptRepository receiptRepository;

    private final String ADD = "add";

    private final String EDIT = "edit";


    public Page<PatientRecordInterfaceDTO> getListPatientRecord(Long patientId, String reason, String diagnostic,
                                                                String causal, String date, String treatment,
                                                                String laboName, String serviceName,
                                                                Pageable pageable) {

        return patientRecordRepository.getAllByPatientId(patientId, reason, diagnostic, causal, date, treatment,
                laboName, serviceName, pageable);

    }

    public PatientRecordDTO getDetailRecord(Long id) {
        PatientRecordInterfaceDTO patientRecordInterfaceDTO = patientRecordRepository.findPatientRecordDtoByPatientRecordId(id);

        if (Objects.isNull(patientRecordInterfaceDTO)) {
            throw new EntityNotFoundException(MessageConstant.PatientRecord.PATIENT_RECORD_NOT_FOUND,
                    EntityName.PatientRecord.PATIENT_RECORD, EntityName.PatientRecord.PATIENT_RECORD_ID);
        }

        PatientRecordDTO patientRecordDTO = new PatientRecordDTO();
        patientRecordDTO.setPatientRecordId(patientRecordInterfaceDTO.getPatientRecordId());
        patientRecordDTO.setReason(patientRecordInterfaceDTO.getReason());
        patientRecordDTO.setDiagnostic(patientRecordInterfaceDTO.getDiagnostic());
        patientRecordDTO.setCausal(patientRecordInterfaceDTO.getCausal());
        patientRecordDTO.setDate(patientRecordInterfaceDTO.getDate());
        patientRecordDTO.setTreatment(patientRecordInterfaceDTO.getTreatment());
        patientRecordDTO.setMarrowRecord(patientRecordInterfaceDTO.getMarrowRecord());
        patientRecordDTO.setNote(patientRecordInterfaceDTO.getNote());
        patientRecordDTO.setPrescription(patientRecordInterfaceDTO.getPrescription());
        patientRecordDTO.setLaboName(patientRecordInterfaceDTO.getLaboName());
        patientRecordDTO.setPrescription(patientRecordInterfaceDTO.getPrescription());
        patientRecordDTO.setServiceName(patientRecordInterfaceDTO.getServices());
        patientRecordDTO.setTreatmentId(patientRecordInterfaceDTO.getTreatmentId());
        List<ServiceDTO> serviceDTOS = serviceRepository.findAllByPatientRecordId(id);

        serviceDTOS.forEach(serviceDTO -> {
            if (Objects.equals(serviceDTO.getStartRecordId(), id)){
                serviceDTO.setIsNew(Boolean.TRUE);
            }
        });

        patientRecordDTO.setServiceDTOS(serviceDTOS);
        patientRecordDTO.setMaterialExportDTOS(materialExportMapper.toDto(materialExportRepository.findAllByPatientRecordId(id)));
        patientRecordDTO.setSpecimensDTOS(specimenMapper.toDto(specimenRepository.findAllByPatientRecordIdAndIsDeleted(id, Boolean.FALSE)));

        return patientRecordDTO;
    }


    public PatientRecordDTO addPatientRecord(String token, Long patientId, PatientRecordDTO patientRecordDTO) {

        Long userId = getUserId(token);

        patientRecordDTO.setPatientRecordId(null);
        patientRecordDTO.setUserId(userId);
        patientRecordDTO.setDate(LocalDate.now());

        Patient patient = patientRepository.findByPatientIdAndIsDeleted(patientId, Boolean.FALSE);

        if (Objects.isNull(patient)) {
            throw new EntityNotFoundException(MessageConstant.PatientRecord.PATIENT_RECORD_NOT_FOUND,
                    EntityName.PatientRecord.PATIENT_RECORD, EntityName.PatientRecord.PATIENT_RECORD_ID);
        }

        if (Objects.equals(patient.getStatus(), StatusConstant.DONE)
                || Objects.equals(patient.getStatus(), StatusConstant.NOT_TREATMENT)) {
            Treatment treatment = new Treatment();
            treatment.setPatientId(patientId);
            treatmentRepository.save(treatment);
            patientRecordDTO.setTreatmentId(treatment.getTreatmentId());
        }

        if (Objects.equals(patient.getStatus(), StatusConstant.TREATING)) {

            Treatment oldTreatment = treatmentRepository.findFirstByPatientIdOrderByTreatmentIdDesc(patientId);
            patientRecordDTO.setTreatmentId(oldTreatment.getTreatmentId());
        }

        patientRecordDTO.setIsDeleted(Boolean.FALSE);
        PatientRecord patientRecord = patientRecordMapper.toEntity(patientRecordDTO);
        patientRecordRepository.save(patientRecord);

        if (CollectionUtils.isEmpty(patientRecordDTO.getServiceDTOS())) {
            throw new AccessDenyException(MessageConstant.Service.SERVICE_CAN_NOT_BE_EMPTY, EntityName.Service.SERVICE);
        }

        saveServiceToTreatmentAndRecord(patient, patientRecordDTO, patientRecord);

        WaitingRoom waitingRoom = waitingRoomRepository.findByPatientIdAndDateAndIsDeleted(patientId, LocalDate.now(), Boolean.FALSE);
        if (Objects.nonNull(waitingRoom)) {
            waitingRoom.setIsDeleted(Boolean.TRUE);
            waitingRoomRepository.save(waitingRoom);
        }

        if (!CollectionUtils.isEmpty(patientRecordDTO.getMaterialExportDTOS())) {
            insertMaterialExport(patientRecord.getPatientRecordId(), patientRecordDTO.getMaterialExportDTOS());
        }

        if (!CollectionUtils.isEmpty(patientRecordDTO.getSpecimensDTOS())) {
            insertLabo(patientRecordDTO.getSpecimensDTOS(), patientRecord.getPatientRecordId());
        }

        Notify notify = new Notify();
        notify.setTreatmentId(patientRecord.getTreatmentId());
        notify.setIsRead(Boolean.FALSE);

        notifyRepository.save(notify);

        return patientRecordMapper.toDto(patientRecord);
    }

    private void insertLabo(List<SpecimensDTO> specimensDTOS, Long patientRecordId) {
        Set<Long> laboIds = specimensDTOS.stream().map(SpecimensDTO::getLaboId).collect(Collectors.toSet());

        List<Labo> labos = laboRepository.findAllByLaboIdInAndIsDeleted(new ArrayList<>(laboIds), Boolean.FALSE);

        if (labos.size() < laboIds.size()) {
            throw new EntityNotFoundException(MessageConstant.Labo.LABO_NOT_FOUND,
                    EntityName.PatientRecord.PATIENT_RECORD, EntityName.Labo.LABO_ID);
        }
        List<Specimen> specimens = specimensDTOS.stream().map(specimensDTO -> {
            Specimen specimen = specimenMapper.toEntity(specimensDTO);
            specimen.setPatientRecordId(patientRecordId);
            specimen.setStatus(StatusConstant.PREPARE_SPECIMEN);
            specimen.setIsDeleted(Boolean.FALSE);

            return specimen;
        }).collect(Collectors.toList());

        specimenRepository.saveAll(specimens);
    }

    public PatientRecordDTO updateRecord(String token, Long id, PatientRecordDTO patientRecordDTO) {
        Long userId = getUserId(token);
        patientRecordDTO.setPatientRecordId(id);
        patientRecordDTO.setUserId(userId);

        Patient patient = patientRepository.findByPatientRecordId(id);
        if (Objects.isNull(patient)) {
            throw new EntityNotFoundException(MessageConstant.PatientRecord.PATIENT_RECORD_NOT_FOUND,
                    EntityName.PatientRecord.PATIENT_RECORD, EntityName.PatientRecord.PATIENT_RECORD_ID);
        }

        PatientRecord patientRecordDb = patientRecordRepository.findByPatientRecordIdAndIsDeleted(id, Boolean.FALSE);
        if (Objects.isNull(patientRecordDb)) {
            throw new EntityNotFoundException(MessageConstant.PatientRecord.PATIENT_RECORD_NOT_FOUND,
                    EntityName.PatientRecord.PATIENT_RECORD, EntityName.PatientRecord.PATIENT_RECORD_ID);
        }

        if (patientRecordDb.getDate().plusDays(1).isBefore(LocalDate.now())) {
            throw new AccessDenyException(MessageConstant.PatientRecord.PATIENT_RECORD_OVER_DATE,
                    EntityName.PatientRecord.PATIENT_RECORD);
        }

        patientRecordDTO.setIsDeleted(Boolean.FALSE);
        PatientRecord patientRecord = patientRecordMapper.toEntity(patientRecordDTO);
        patientRecordRepository.save(patientRecord);

        treatmentServiceMapRepository.deleteAllByStartRecordId(id);
        patientRecordServiceMapRepository.deleteAllByPatientRecordId(id);

        saveServiceToTreatmentAndRecord(patient, patientRecordDTO, patientRecord);


        List<MaterialExport> materialExports = materialExportRepository.findAllByPatientRecordId(id);
        List<Long> oldMaterialExportsIds = materialExports.stream().map(MaterialExport::getMaterialExportId).collect(Collectors.toList());
        List<Long> newMaterialExportsIds = patientRecordDTO.getMaterialExportDTOS().stream()
                .map(MaterialExportDTO::getMaterialExportId).collect(Collectors.toList());
        oldMaterialExportsIds.removeAll(newMaterialExportsIds);
        materialExportRepository.deleteAllById(oldMaterialExportsIds);

        if (!CollectionUtils.isEmpty(patientRecordDTO.getMaterialExportDTOS())) {
            updateMaterialExport(patientRecordDTO.getMaterialExportDTOS(), id);
        }

        List<Specimen> specimens = specimenRepository.findAllByPatientRecordIdAndIsDeleted(id, Boolean.FALSE);
        List<Long> oldSpecimenIds = specimens.stream().map(Specimen::getSpecimenId).collect(Collectors.toList());
        List<Long> newSpecimenIds = patientRecordDTO.getSpecimensDTOS().stream()
                .map(SpecimensDTO::getSpecimenId).collect(Collectors.toList());
        oldSpecimenIds.removeAll(newSpecimenIds);
        specimenRepository.deleteAllById(oldSpecimenIds);

        if (!CollectionUtils.isEmpty(patientRecordDTO.getSpecimensDTOS())) {
            updateSpecimen(patientRecordDTO.getSpecimensDTOS(), id);
        }

        return patientRecordMapper.toDto(patientRecord);
    }

    private void updateSpecimen(List<SpecimensDTO> specimensDTOS, Long recordId) {

        Set<Long> laboIds = specimensDTOS.stream().filter(specimensDTO -> Objects.equals(ADD, specimensDTO.getStatusChange())
                        || Objects.equals(EDIT, specimensDTO.getStatusChange()))
                .map(SpecimensDTO::getLaboId).collect(Collectors.toSet());
        List<Labo> labos = laboRepository.findAllByLaboIdInAndIsDeleted(new ArrayList<>(laboIds), Boolean.FALSE);

        if (labos.size() < laboIds.size()) {
            throw new EntityNotFoundException(MessageConstant.Labo.LABO_NOT_FOUND,
                    EntityName.PatientRecord.PATIENT_RECORD, EntityName.Labo.LABO_ID);
        }
        List<Long> editIds = specimensDTOS.stream().filter(specimensDTO -> Objects.equals(EDIT, specimensDTO.getStatusChange()))
                .map(SpecimensDTO::getSpecimenId).collect(Collectors.toList());
        List<Specimen> specimenEdit = specimenRepository.findAllBySpecimenIdInAndIsDeleted(editIds, Boolean.FALSE);

        if (specimenEdit.size() < editIds.size()) {
            throw new EntityNotFoundException(MessageConstant.Specimen.SPECIMEN_NOT_FOUND,
                    EntityName.Specimen.SPECIMEN, EntityName.Specimen.SPECIMEN_ID);
        }

        Map<Long, Specimen> mapSpecimen = specimenEdit.stream()
                .collect(Collectors.toMap(Specimen::getSpecimenId, Function.identity()));
        specimensDTOS.stream()
                .filter(specimensDTO -> Objects.equals(ADD, specimensDTO.getStatusChange())
                        || Objects.equals(EDIT, specimensDTO.getStatusChange()))
                .forEach(specimensDTO -> {

                    if (specimensDTO.getStatusChange().equals(ADD)) {
                        specimensDTO.setSpecimenId(null);
                        specimensDTO.setStatus(StatusConstant.PREPARE_SPECIMEN);
                    }

                    if (specimensDTO.getStatusChange().equals(EDIT)) {
                        specimensDTO.setStatus(mapSpecimen.get(specimensDTO.getSpecimenId()).getStatus());
                    }
                    specimensDTO.setPatientRecordId(recordId);
                    specimensDTO.setIsDeleted(Boolean.FALSE);
        });
        List<Specimen> specimen = specimenMapper.toEntity(specimensDTOS);
        specimenRepository.saveAll(specimen);
    }

    private void updateMaterialExport(List<MaterialExportDTO> materialExportDTOS, Long recordId) {
        List<Long> materialIds = materialExportDTOS.stream()
                .filter(materialExportDTO -> Objects.equals(ADD, materialExportDTO.getStatusChange())
                        || Objects.equals(EDIT, materialExportDTO.getStatusChange()))
                .map(MaterialExportDTO::getMaterialId).collect(Collectors.toList());

        Map<Long, Material> mapMaterial = materialRepository.findAllByMaterialIdIn(materialIds).stream()
                .collect(Collectors.toMap(Material::getMaterialId, Function.identity()));

        List<Long> exportEditIds = materialExportDTOS.stream()
                .filter(materialExportDTO -> Objects.equals(EDIT, materialExportDTO.getStatusChange()))
                .map(MaterialExportDTO::getMaterialExportId).collect(Collectors.toList());

        List<MaterialExport> materialExportEdit = materialExportRepository.findAllByMaterialExportIdInAndIsDelete(exportEditIds, Boolean.FALSE);
        if (exportEditIds.size() > materialExportEdit.size()) {
            throw new EntityNotFoundException(MessageConstant.MaterialExport.MATERIAL_EXPORT_NOT_FOUND,
                    EntityName.MaterialExport.MATERIAL_EXPORT, EntityName.MaterialExport.MATERIAL_EXPORT_ID);
        }
        Map<Long, MaterialExport> mapMaterialExport = materialExportEdit.stream()
                .collect(Collectors.toMap(MaterialExport::getMaterialExportId, Function.identity()));

        materialExportDTOS.stream()
                .filter(materialExportDTO -> Objects.equals(ADD, materialExportDTO.getStatusChange())
                        || Objects.equals(EDIT, materialExportDTO.getStatusChange()))
                .forEach(materialExportDTO -> {

                    if (materialExportDTO.getStatusChange().equals(ADD)) {
                        materialExportDTO.setMaterialExportId(null);
                        mapMaterial.get(materialExportDTO.getMaterialId())
                                .setAmount(mapMaterial.get(materialExportDTO.getMaterialId()).getAmount() - materialExportDTO.getAmount());
                    }

                    if (materialExportDTO.getStatusChange().equals(EDIT)) {
                        mapMaterial.get(materialExportDTO.getMaterialId())
                                .setAmount(mapMaterial.get(materialExportDTO.getMaterialId()).getAmount()
                                        + mapMaterialExport.get(materialExportDTO.getMaterialExportId()).getAmount()
                                        - materialExportDTO.getAmount());
                    }

                    materialExportDTO.setIsDelete(Boolean.FALSE);
                    materialExportDTO.setPatientRecordId(recordId);
                });

        List<Material> materialError = mapMaterial.values().stream().filter(material -> material.getAmount() < 0).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(materialError)) {
            throw new AccessDenyException(MessageConstant.Material.NOT_ENOUGH_MATERIAL, EntityName.Material.MATERIAL);
        }

        materialRepository.saveAll(mapMaterial.values());
        materialExportRepository.saveAll(materialExportMapper.toEntity(materialExportDTOS));
    }

    private void insertMaterialExport(Long patientRecordId, List<MaterialExportDTO> materialExportDTOS) {
        List<Long> materialIds = materialExportDTOS.stream().map(MaterialExportDTO::getMaterialId)
                .collect(Collectors.toList());

        List<Material> materials = materialRepository.findAllByMaterialIdIn(materialIds);
        if (materials.size() < materialIds.size()) {
            throw new EntityNotFoundException(MessageConstant.Material.MATERIAL_NOT_FOUND,
                    EntityName.PatientRecord.PATIENT_RECORD, EntityName.Material.MATERIAL_ID);
        }

        materials.forEach(material ->
            materialExportDTOS.stream()
                    .filter(me -> Objects.equals(material.getMaterialId(), me.getMaterialId()))
                    .findFirst()
                    .ifPresent(materialExport -> {
                if (material.getAmount() - materialExport.getAmount() >= 0) {
                    material.setAmount(material.getAmount() - materialExport.getAmount());
                } else {
                    throw new AccessDenyException(MessageConstant.Material.NOT_ENOUGH_MATERIAL, EntityName.Material.MATERIAL);
                }
            })
        );
        materialRepository.saveAll(materials);

        materialExportDTOS.forEach(materialExport -> {
            materialExport.setMaterialExportId(null);
            materialExport.setIsDelete(Boolean.FALSE);
            materialExport.setPatientRecordId(patientRecordId);
            materialExport.setIsShow(Boolean.FALSE);
        });
        materialExportRepository.saveAll(materialExportMapper.toEntity(materialExportDTOS));
    }

    private void saveServiceToTreatmentAndRecord(Patient patient, PatientRecordDTO patientRecordDTO, PatientRecord patientRecord) {
        List<TreatmentServiceMap> treatmentServiceMaps = new ArrayList<>();
        List<PatientRecordServiceMap> patientRecordServiceMaps = new ArrayList<>();

        patientRecordDTO.getServiceDTOS().forEach(serviceDTO -> {
            PatientRecordServiceMap patientRecordServiceMap = new PatientRecordServiceMap();
            patientRecordServiceMap.setStartRecordId(serviceDTO.getStartRecordId());
            patientRecordServiceMap.setPatientRecordServiceMapId(null);
            patientRecordServiceMap.setPatientRecordId(patientRecord.getPatientRecordId());
            patientRecordServiceMap.setServiceId(serviceDTO.getServiceId());
            patientRecordServiceMap.setStatus(serviceDTO.getStatus());

            if (Objects.equals(serviceDTO.getIsNew(), Boolean.TRUE)) {
                TreatmentServiceMap treatmentServiceMap = new TreatmentServiceMap();
                treatmentServiceMap.setTreatmentServiceMapId(null);
                treatmentServiceMap.setTreatmentId(patientRecordDTO.getTreatmentId());
                treatmentServiceMap.setCurrentPrice(serviceDTO.getPrice());
                treatmentServiceMap.setDiscount(Objects.nonNull(serviceDTO.getDiscount()) ? serviceDTO.getDiscount() : 0);
                treatmentServiceMap.setServiceId(serviceDTO.getServiceId());
                treatmentServiceMap.setStartRecordId(patientRecord.getPatientRecordId());
                treatmentServiceMap.setAmount(Objects.nonNull(serviceDTO.getAmount()) ? serviceDTO.getAmount() : 1);
                treatmentServiceMap.setIsShow(Boolean.FALSE);
                treatmentServiceMaps.add(treatmentServiceMap);

                patientRecordServiceMap.setStartRecordId(patientRecord.getPatientRecordId());
            }
            patientRecordServiceMaps.add(patientRecordServiceMap);
        });

        // Update date status of patient when all service is done.
        List<ServiceDTO> listServiceNotDone = patientRecordDTO.getServiceDTOS().stream()
                .filter(serviceDTO -> Objects.equals(serviceDTO.getStatus(), StatusConstant.TREATING)).collect(Collectors.toList());

        Integer getDebit = receiptRepository.getDebit(patientRecordDTO.getTreatmentId()).stream().findFirst().orElse(0);
        if (CollectionUtils.isEmpty(listServiceNotDone) && getDebit == 0) {
            patient.setStatus(StatusConstant.DONE);
        } else {
            patient.setStatus(StatusConstant.TREATING);
        }

        patientRepository.save(patient);

        patientRecordServiceMapRepository.saveAll(patientRecordServiceMaps);
        treatmentServiceMapRepository.saveAll(treatmentServiceMaps);

        // Handle specimen when service done.
        List<Long> serviceIdsDone = patientRecordDTO.getServiceDTOS().stream()
                .filter(serviceDTO -> Objects.equals(serviceDTO.getStatus(), StatusConstant.DONE))
                .map(ServiceDTO::getServiceId).collect(Collectors.toList());

        List<Long> startRecordIds = treatmentServiceMapRepository.findAllStartRecordByTreatmentIdAndListServiceId(patientRecord.getTreatmentId(), serviceIdsDone);
        List<Specimen> specimens = specimenRepository.findAllByPatientRecordIdInAndServiceIdInAndIsDeleted(startRecordIds, serviceIdsDone, Boolean.FALSE);
        specimens.forEach(specimen -> {
            specimen.setStatus(StatusConstant.SPECIMEN_COMPLETED);
            specimen.setUsedDate(LocalDate.now());
        });
        specimenRepository.saveAll(specimens);

    }

    public void deleteRecord(Long id) {

        PatientRecord patientRecordDb = patientRecordRepository.findByPatientRecordIdAndIsDeleted(id, Boolean.FALSE);
        if (Objects.isNull(patientRecordDb)) {
            throw new EntityNotFoundException(MessageConstant.PatientRecord.PATIENT_RECORD_NOT_FOUND,
                    EntityName.PatientRecord.PATIENT_RECORD, EntityName.PatientRecord.PATIENT_RECORD_ID);
        }

        if (patientRecordDb.getDate().plusDays(1).isBefore(LocalDate.now())) {
            throw new AccessDenyException(MessageConstant.PatientRecord.PATIENT_RECORD_OVER_DATE,
                    EntityName.PatientRecord.PATIENT_RECORD);
        }

        patientRecordServiceMapRepository.deleteAllByPatientRecordId(id);
        treatmentServiceMapRepository.deleteAllByStartRecordId(id);
        specimenRepository.deleteAllByPatientRecordId(id);

        patientRecordDb.setIsDeleted(Boolean.TRUE);
        patientRecordRepository.save(patientRecordDb);
    }

    public List<PatientRecordDTO> getAllRecord(Long patientId, String date) {

        if (!StringUtils.hasLength(date)) {
            date = "";
        }
        return patientRecordMapper.toDto(patientRecordRepository.findRecordByPatientId(patientId, date));
    }
}
