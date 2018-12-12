package com.orange.verify.builder.config;

import com.orange.verify.common.BaseEntity;
import lombok.extern.java.Log;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@Log
public class MySQLCommentGenerator extends EmptyCommentGenerator {

    private Properties properties;

    public MySQLCommentGenerator() {
        properties = new Properties();
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        // 获取自定义的 properties
        this.properties.putAll(properties);
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        String author = properties.getProperty("author");
        String dateFormat = properties.getProperty("dateFormat", "yyyy-MM-dd");
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);

        FullyQualifiedTable fullyQualifiedTable = introspectedTable.getFullyQualifiedTable();

        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(new FullyQualifiedJavaType("long"));
        field.setStatic(true);
        field.setFinal(true);
        field.setName("serialVersionUID");
        field.setInitializationString("1L");
        topLevelClass.addField(field);

        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("Serializable");
        FullyQualifiedJavaType imp = new FullyQualifiedJavaType("java.io.Serializable");
        FullyQualifiedJavaType imp2 = new FullyQualifiedJavaType("com.baomidou.mybatisplus.annotation.*");
        topLevelClass.addSuperInterface(fqjt);
        topLevelClass.addImportedType(imp);
        topLevelClass.addImportedType(imp2);

        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * " + introspectedTable.getRemarks());
        topLevelClass.addJavaDocLine(" * " + fullyQualifiedTable.getIntrospectedTableName());
        topLevelClass.addJavaDocLine(" * @author " + author);
        topLevelClass.addJavaDocLine(" * @date " + dateFormatter.format(new Date()));
        topLevelClass.addJavaDocLine(" */");
        topLevelClass.addJavaDocLine("@TableName(\""+ fullyQualifiedTable.getIntrospectedTableName() +"\")");
        topLevelClass.addJavaDocLine("@KeySequence(\"SEQ_TEST\")");

        log.info("数据库表：" + fullyQualifiedTable.getIntrospectedTableName() + " 生成...");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

        // 获取列注释
        String remarks = introspectedColumn.getRemarks();

        if (introspectedColumn.getJavaProperty().equals("id")) {
            field.addJavaDocLine("@TableId(value = \""+ introspectedColumn.getActualColumnName() +"\", type = IdType.ID_WORKER_STR)");
        }
        else if (introspectedColumn.getJavaProperty().equals("delFlag")) {
            field.addJavaDocLine("@TableLogic");
        }
        else if (introspectedColumn.getJavaProperty().equals("createDate")) {
            field.addJavaDocLine("@TableField(value = \""+ introspectedColumn.getActualColumnName() +"\",fill = FieldFill.INSERT)");
        }
        else if (introspectedColumn.getJavaProperty().equals("updateDate")) {
            field.addJavaDocLine("@TableField(value = \""+ introspectedColumn.getActualColumnName() +"\",fill = FieldFill.UPDATE)");
        }
        else {
            field.addJavaDocLine("/**");
            if (!remarks.equals("")) {
                field.addJavaDocLine(" * " + remarks);
            }
            field.addJavaDocLine(" * " + introspectedColumn.getActualColumnName());
            field.addJavaDocLine(" */");
        }



    }
}
