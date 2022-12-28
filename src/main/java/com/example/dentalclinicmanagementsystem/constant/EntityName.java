package com.example.dentalclinicmanagementsystem.constant;

public class EntityName {

    public static class User {
        private User(){};

        public static final String USER = "user";
        public static final String USER_ID = "userId";
        public static final String USERNAME = "username";
        public static final String EMAIL = "email";

    }

    public static class Material{
        private Material(){}

        public static final String MATERIAL = "material";
        public static final String MATERIAL_ID = "materialId";
        public static final String MATERIAL_NAME = "materialName";
    }

    public static class MaterialImport{
        private MaterialImport(){}

        public static final String MATERIAL_IMPORT = "materialImport";
        public static final String MATERIAL_IMPORT_ID = "materialImportId";
    }

    public static class CategoryService {
        private CategoryService(){}

        public static final String CATEGORY = "category";
        public static final String CATEGORY_ID = "categoryId";
        public static final String CATEGORY_NAME = "categoryName";

    }

    public static class Service {

        private Service(){}

        public static final String SERVICE = "service";
        public static final String SERVICE_ID = "serviceId";
        public static final String SERVICE_NAME = "serviceName";
    }

    public static class Patient {

        private Patient(){}

        public static final String PATIENT = "patient";
        public static final String PATIENT_ID = "patientId";
    }

    public static class PatientRecord {

        private PatientRecord(){}

        public static final String PATIENT_RECORD = "patientRecord";
        public static final String PATIENT_RECORD_ID = "patientRecordId";

        public static final String PRE_RECORD = "preRecord";
    }

    public static class Labo {
        private Labo(){}

        public static final String LABO = "labo";
        public static final String LABO_ID = "laboId";
    }

    public static class MaterialExport{
        private MaterialExport(){}

        public static final String MATERIAL_EXPORT = "materialExport";
        public static final String MATERIAL_EXPORT_ID = "materialExportId";
    }

    public static class Receipt {
        private Receipt(){}

        public static final String RECEIPT = "receipt";
        public static final String RECEIPT_ID = "receiptId";
        public static final String TREATMENT_ID = "treatmentId";
    }

    public static class Timekeeping {
        private Timekeeping(){}

        public static final String TIMEKEEPING = "timekeeping";
    }

    public static class Bill {
        private Bill(){}

        public static final String BILL = "bill";
        public static final String BILL_ID = "billId";
    }

    public static class Specimen {
        private Specimen(){}

        public static final String SPECIMEN = "specimen";
        public static final String SPECIMEN_ID = "specimenId";
        public static final String PATIENT_RECORD_NOT_FOUND = "patient record not found";
        public static final String LABO_NOT_FOUND = "labo not found";

    }

    public static class WaitingRoom {
        private WaitingRoom(){}

        public static final String WAITING_ROOM = "waitingRoom";
        public static final String WAITING_ROOM_ID = "waitingRoomId";
    }
}
