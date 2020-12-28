CREATE TABLE rajshekhar_basu_mahabharat_bangla_corrected_word (id INT AUTO_INCREMENT NOT NULL, user_id INT NOT NULL, ocr_word_id INT NOT NULL, corrected_text LONGTEXT NULL, ignored BIT(1) DEFAULT 0 NOT NULL, CONSTRAINT PK_CORRECTED_WORD PRIMARY KEY (id));

CREATE TABLE rajshekhar_basu_mahabharat_bangla_ocr_word (book_id INT NOT NULL, page_image_id INT NOT NULL, word_sequence_id INT NOT NULL, raw_text LONGTEXT NULL, x1 INT NOT NULL, y1 INT NOT NULL, x2 INT NOT NULL, y2 INT NOT NULL, confidence DOUBLE NOT NULL, line_number INT NULL, id INT AUTO_INCREMENT NOT NULL, CONSTRAINT PK_OCR_WORD PRIMARY KEY (id));

CREATE INDEX SYS_FK_49127 ON rajshekhar_basu_mahabharat_bangla_corrected_word(ocr_word_id);

CREATE INDEX SYS_FK_49129 ON rajshekhar_basu_mahabharat_bangla_corrected_word(user_id);

CREATE INDEX SYS_IDX_SYS_FK_10143_10156 ON rajshekhar_basu_mahabharat_bangla_ocr_word(book_id);

CREATE INDEX SYS_IDX_SYS_FK_10144_10158 ON rajshekhar_basu_mahabharat_bangla_ocr_word(page_image_id);

ALTER TABLE rajshekhar_basu_mahabharat_bangla_ocr_word ADD CONSTRAINT SYS_FK_10143 FOREIGN KEY (book_id) REFERENCES book (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE rajshekhar_basu_mahabharat_bangla_ocr_word ADD CONSTRAINT SYS_FK_10144 FOREIGN KEY (page_image_id) REFERENCES page_image (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE rajshekhar_basu_mahabharat_bangla_corrected_word ADD CONSTRAINT SYS_FK_49127 FOREIGN KEY (ocr_word_id) REFERENCES rajshekhar_basu_mahabharat_bangla_ocr_word (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE rajshekhar_basu_mahabharat_bangla_corrected_word ADD CONSTRAINT SYS_FK_49129 FOREIGN KEY (user_id) REFERENCES user (id) ON UPDATE RESTRICT ON DELETE RESTRICT;
