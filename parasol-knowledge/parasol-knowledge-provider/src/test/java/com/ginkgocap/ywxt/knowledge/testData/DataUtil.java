package com.ginkgocap.ywxt.knowledge.testData;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ginkgocap.parasol.knowledge.model.Knowledge;
import com.ginkgocap.parasol.knowledge.model.common.ColumnType;

import java.io.*;

/**
 * Created by Admin on 2016/3/17.
 */
public final class DataUtil {
    public static String jsonPath = null;
    private static ObjectMapper objectMapper = null;
    static {
        if (DataUtil.jsonPath == null) {
            DataUtil.jsonPath = defaultJsonPath();
        }
        objectMapper = new ObjectMapper();
    }

    public static Knowledge getKnowledgeObject(ColumnType columnType)
    {
        Knowledge knowledge = new Knowledge();
        knowledge.setTitle("KnowledgeTitle");
        knowledge.setContent("Knowledge Content");
        knowledge.setCname(columnType.getClass().getSimpleName());
        knowledge.setCid(123456L);
        knowledge.setColumnid("12345");
        knowledge.setColumnType(columnType.getVal());
        knowledge.setCpathid("12324");
        knowledge.setCreatetime(System.currentTimeMillis());
        knowledge.setDesc("Knowledge Description");
        knowledge.setStatus(1);

        return knowledge;
    }

    public static Knowledge getKnowledgeNews()
    {
        return getKnowledgeObject(ColumnType.EKnowledgeNews);
    }

    public static Knowledge getKnowledgeInvestment()
    {
        return getKnowledgeObject(ColumnType.EKnowledgeInvestment);
    }

    public static Knowledge getKnowledgeIndustry()
    {
        return getKnowledgeObject(ColumnType.EKnowledgeIndustry);
    }

    public static Knowledge getKnowledgeCase()
    {
        return getKnowledgeObject(ColumnType.EKnowledgeCase);
    }

    public static Knowledge getKnowledgeNewMaterials()
    {
        return getKnowledgeObject(ColumnType.EKnowledgeNewMaterials);
    }

    public static Knowledge getKnowledgeAsset()
    {
        return getKnowledgeObject(ColumnType.EKnowledgeAsset);
    }

    public static Knowledge getKnowledgeMacro()
    {
        return getKnowledgeObject(ColumnType.EKnowledgeMacro);
    }


    public static Knowledge getKnowledgeOpinion()
    {
        return getKnowledgeObject(ColumnType.EKnowledgeOpinion);
    }

    public static Knowledge getKnowledgeLaw()
    {
        return getKnowledgeObject(ColumnType.EKnowledgeLaw);
    }

    public static Knowledge getKnowledgeArticle()
    {
        return getKnowledgeObject(ColumnType.EKnowledgeArticle);
    }

    public static Knowledge getEKnowledgeInternet()
    {
        return getKnowledgeObject(ColumnType.EKnowledgeInternet);
    }

    public static Knowledge getKnowledgeSales()
    {
        return getKnowledgeObject(ColumnType.EKnowledgeSales);
    }

    public static Knowledge getKnowledgeFinance()
    {
        return getKnowledgeObject(ColumnType.EKnowledgeFinance);
    }


    //Help method
    public static String getJsonFile(String fileName)
    {
        return jsonPath + fileName + ".json";
    }

    public static void writeJsonData(Knowledge knowledge) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonFile = getJsonFile(knowledge.getClass().getSimpleName());
            String jsonContent = objectMapper.writeValueAsString(knowledge);
            System.out.print(jsonContent);
            //objectMapper.writeValue(new File(jsonFile), jsonContent);
            writeToFile(jsonFile, jsonContent);
            //Add json to API document
            //appendJson(reference,jsonContent);
            //End
        } catch (JsonGenerationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static String jsonNode(String jsonNode)
    {
        return String.format("\"%s\"",jsonNode);
    }

    public static String getJsonContentFromFile(String filePath)
    {
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println(filePath + " not exist!");
                return null;
            }
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                buffer.append(tempString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        return buffer.toString();
    }

    public static void writeToFile(String jsonFile, String jsonContent) {
        BufferedWriter bw = null;
        try {
            File file = new File(jsonFile);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            bw = new BufferedWriter(fw);
            bw.write(jsonContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public static String defaultJsonPath()
    {
        String defaultJsonPath = null;
        String usrHome = System.getProperty("user.home");
        String fileSeparator = System.getProperty("file.separator");
        defaultJsonPath = new StringBuffer().append(usrHome)
                .append(fileSeparator)
                .append("JSON")
                .append(fileSeparator).toString();
        File file = new File(defaultJsonPath);
        if (!file.exists()) {
            file.mkdir();
        }
        return defaultJsonPath;
    }
}
