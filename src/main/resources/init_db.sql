CREATE SCHEMA `daohw` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `daohw`.`manufacturers` (
                                         `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                         `name` VARCHAR(45) NOT NULL,
                                         `country` VARCHAR(45) NOT NULL,
                                         PRIMARY KEY (`id`),
                                         UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);
ALTER TABLE `daohw`.`manufacturers`
    ADD COLUMN `deleted` TINYINT NULL DEFAULT 0 AFTER `country`,
DROP INDEX `name_UNIQUE` ;
;

CREATE TABLE `daohw`.`drivers` (
                                   `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                   `name` VARCHAR(45) NOT NULL,
                                   `license_number` VARCHAR(45) NOT NULL,
                                   `deleted` TINYINT NOT NULL DEFAULT 0,
                                   PRIMARY KEY (`id`));

CREATE TABLE `daohw`.`cars` (
                                `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                `manufacturer_id` BIGINT(11) NOT NULL,
                                `model` VARCHAR(150) NULL,
                                `deleted` TINYINT NOT NULL DEFAULT 0,
                                PRIMARY KEY (`id`));
ALTER TABLE `daohw`.`cars`
    ADD COLUMN `carscol` VARCHAR(45) NOT NULL AFTER `deleted`,
    CHANGE COLUMN `model` `model` VARCHAR(150) NOT NULL ;
ALTER TABLE `daohw`.`cars`
    DROP COLUMN `carscol`;



ALTER TABLE `daohw`.`cars`
    ADD INDEX `cars_manufacturer_fk_idx` (`manufacturer_id` ASC) VISIBLE;
;
ALTER TABLE `daohw`.`cars`
    ADD CONSTRAINT `cars_manufacturer_fk`
        FOREIGN KEY (`manufacturer_id`)
            REFERENCES `daohw`.`manufacturers` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;

CREATE TABLE `daohw`.`cars_drivers` (
                                        `driver_id` BIGINT(11) NOT NULL,
                                        `car_id` BIGINT(11) NOT NULL);

ALTER TABLE `daohw`.`cars_drivers`
    ADD INDEX `car_driver_fk_idx` (`driver_id` ASC) VISIBLE,
    ADD INDEX `car_tocar_fk_idx` (`car_id` ASC) VISIBLE;
;
ALTER TABLE `daohw`.`cars_drivers`
    ADD CONSTRAINT `car_driver_fk`
        FOREIGN KEY (`driver_id`)
            REFERENCES `daohw`.`drivers` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    ADD CONSTRAINT `car_tocar_fk`
        FOREIGN KEY (`car_id`)
            REFERENCES `daohw`.`cars` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;
