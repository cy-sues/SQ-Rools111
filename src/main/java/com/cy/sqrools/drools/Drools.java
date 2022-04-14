package com.cy.sqrools.drools;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Drools {
    // 获取drools实现的 KieServices 实例
    KieServices ks = KieServices.Factory.get();
    // kieServices默认加载 classpath:META-INF/kmodule.xml 得到 KieContainer
    KieContainer kContainer = ks.getKieClasspathContainer();
    // 通过 kContainer获取 kmodule.xml 中定义的 ksession
    KieSession kSession = kContainer.newKieSession("shangqi-rules");
    public List<String> read(String filePath){
        ArrayList<String> triples2=new ArrayList<String>();
        try
        {
            String encoding = "utf-8";
            File file;
            file = new File(filePath);
            if (file.isFile() && file.exists())
            {   InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                String dataLine =null;
                while((dataLine = bufferedReader.readLine())!=null){
                    if(dataLine.isEmpty())
                        continue;
                    else{
                        String[] lineArray = dataLine.split(" ");
                        // 向WorkingMemory插入三元组
                        kSession.insert(new Triple(lineArray[0], lineArray[1], lineArray[2]));
                    }
                }
                ArrayList<String> triples1=new ArrayList<String>();
                Object[] a =kSession.getObjects().toArray();
                long k=kSession.getObjects().toArray().length;
                for (Object o:a)
                {
                    String r=o.toString();
                    triples1.add(r);

                }
//                System.out.println(triples1);
                long startTime = System.currentTimeMillis();

//                System.out.println("new new Execute..."+"******************************************************");
                // 使规则引擎执行规则
                kSession.fireAllRules();
                long endTime = System.currentTimeMillis();
                long runningTime = endTime - startTime;
                long newTriple = kSession.getObjects().toArray().length-k;

                // 输出推理后的三元组及数量
//                System.out.println("Facts num after reasoning:" + newTriple);
//                System.out.println("Facts After Reasoning:");
//                min1.add(newTriple);//
                Object[] b =kSession.getObjects().toArray();
                for (Object o:b)
                {
                    String r=o.toString();
                    triples2.add(r);
                }
                triples2.removeAll(triples1);

                // 判断文件是否存在


//
//                while ((lineTxt = bufferedReader.readLine()) != null)
//                {
//                    list.add(lineTxt);
//                }
//                bufferedReader.close();
//                read.close();
                return triples2;
            }
            else
            {
                System.out.println("找不到指定的文件");
                return null;
            }
        }
        catch (Exception e)
        {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

        return triples2;
    }
}
