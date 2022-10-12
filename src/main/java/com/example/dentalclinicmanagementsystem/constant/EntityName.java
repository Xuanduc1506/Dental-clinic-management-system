package com.example.dentalclinicmanagementsystem.constant;

public class EntityName {

    public static class User {
        private User(){};

        public static final String USER = "user";
        public static final String USER_ID = "userId";
        public static final String USERNAME = "username";

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
}
