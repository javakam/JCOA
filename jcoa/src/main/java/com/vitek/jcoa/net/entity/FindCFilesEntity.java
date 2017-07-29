package com.vitek.jcoa.net.entity;

import java.util.List;

/**
 * 获取批文附件/jc_oa/flow/find_cfiles（GET）
 * <p>
 * Created by javakam on 2017/6/3 0003.
 */
public class FindCFilesEntity extends BaseEntity {

    private List<ModelsBean> models;

    public List<ModelsBean> getModels() {
        return models;
    }

    public void setModels(List<ModelsBean> models) {
        this.models = models;
    }

    public static class ModelsBean {
        /**
         * id : 76
         * c_id : 96
         * filename : img1496065691430.png
         * file_type : this_d
         * file_path : /cfiles/0d1fcfdb-87b9-4054-a90f-fc4fa8a96e17.png
         */

        private int id;
        private int c_id;
        private String filename;
        private String file_type;
        private String file_path;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getC_id() {
            return c_id;
        }

        public void setC_id(int c_id) {
            this.c_id = c_id;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getFile_type() {
            return file_type;
        }

        public void setFile_type(String file_type) {
            this.file_type = file_type;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }
    }
}
