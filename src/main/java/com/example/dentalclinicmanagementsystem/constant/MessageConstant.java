package com.example.dentalclinicmanagementsystem.constant;

public class MessageConstant {

    public static class User{
        private User(){}

        public static final String USER_NOT_FOUND = "user not found";
        public static final String USERNAME_NOT_FOUND = "username not found";

        public static final String WRONG_PASSWORD = "wrong password";
    }

    public static class Material{
        private Material(){}

        public static final String MATERIAL_NOT_FOUND = "material not found";
        public static final String MATERIAL_NAME_ALREADY_EXIST = "material name already exist";

    }

    public static class MaterialImport{
        private MaterialImport(){}

        public static final String MATERIAL_IMPORT_NOT_FOUND = "material import not found";
    }

}
