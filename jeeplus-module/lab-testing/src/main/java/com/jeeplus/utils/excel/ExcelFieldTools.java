package com.jeeplus.utils.excel;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.Reflections;
import com.jeeplus.sys.domain.Area;
import com.jeeplus.sys.domain.Office;
import com.jeeplus.sys.domain.User;
import com.jeeplus.sys.utils.DictUtils;
import com.jeeplus.sys.utils.UserUtils;
import com.jeeplus.utils.excel.annotation.ExcelField;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelFieldTools {


    public static <E> List<Object[]> getHeaderListByExcelField(Class<E> cls, int... groups){
        List<Object[]> annotationList = Lists.newArrayList();
        Field[] fs = cls.getDeclaredFields();
        Field[] var8 = fs;
        int i = fs.length;

        int g;
        int var13;
        int efg;
        int var17;
//      注解在成员属性上
        for(int var6 = 0; var6 < i; ++var6) {
            Field f = var8[var6];
            ExcelField ef = (ExcelField)f.getAnnotation(ExcelField.class);
            if (ef != null && (ef.type() == 0 || ef.type() == 2)) {
                if (groups != null && groups.length > 0) {
                    boolean inGroup = false;
                    int[] var14 = groups;
                    var13 = groups.length;

                    for(g = 0; g < var13; ++g) {
                        int g2 = var14[g];
                        if (inGroup) {
                            break;
                        }

                        int[] var18;
                        var17 = (var18 = ef.groups()).length;

                        for(efg = 0; efg < var17; ++efg) {
                            int efg2 = var18[efg];
                            if (g2 == efg2) {
                                inGroup = true;
                                annotationList.add(new Object[]{ef, f});
                                break;
                            }
                        }
                    }
                } else {
                    annotationList.add(new Object[]{ef, f});
                }
            }
        }
        //注解在成员方法上
        Method[] ms = cls.getDeclaredMethods();
        Method[] var29 = ms;
        int var27 = ms.length;

        for(i = 0; i < var27; ++i) {
            Method m = var29[i];
            ExcelField ef = (ExcelField)m.getAnnotation(ExcelField.class);
            if (ef != null && (ef.type() == 0 || ef.type() == 2)) {
                if (groups != null && groups.length > 0) {
                    boolean inGroup = false;
                    int[] var39 = groups;
                    int var37 = groups.length;

                    for(var13 = 0; var13 < var37; ++var13) {
                        g = var39[var13];
                        if (inGroup) {
                            break;
                        }

                        int[] var19;
                        int var43 = (var19 = ef.groups()).length;

                        for(var17 = 0; var17 < var43; ++var17) {
                            efg = var19[var17];
                            if (g == efg) {
                                inGroup = true;
                                annotationList.add(new Object[]{ef, m});
                                break;
                            }
                        }
                    }
                } else {
                    annotationList.add(new Object[]{ef, m});
                }
            }
        }

        Collections.sort(annotationList, new Comparator<Object[]>() {
            public int compare(Object[] o1, Object[] o2) {
                return (new Integer(((ExcelField)o1[0]).sort())).compareTo(new Integer(((ExcelField)o2[0]).sort()));
            }
        });
        return annotationList;
    }

    /**
     * 根据注解的表头映射每一行的每一列到指定的实体属性上
     * 因为是按流的形式加载的文件内容，因此无法指定行数
     * @param sheet 工作簿
     * @param cls 实体类
     * @param headerList 头部注解信息集合
     * @param <E> 泛型
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <E> List<E> getDataListByHeader(Sheet sheet, Class<E> cls, List<Object[]> headerList) throws IllegalAccessException, InstantiationException {
        List<E> dataList = Lists.newArrayList();
        for(Row row:sheet){
            E e = cls.newInstance();
            int column = 0;
            StringBuilder sb = new StringBuilder();

            Object val;
            for(Iterator var36 = headerList.iterator(); var36.hasNext(); sb.append(val + ", ")) {
                Object[] os = (Object[])var36.next();
                val = getCellValue(row, column++);
                if (val != null) {
                    ExcelField ef = (ExcelField)os[0];
                    if (StringUtils.isNotBlank(ef.dictType())) {
                        val = DictUtils.getDictValue(val.toString(), ef.dictType(), "");
                    }

                    Class<?> valType = Class.class;
                    if (os[1] instanceof Field) {
                        valType = ((Field)os[1]).getType();
                    } else if (os[1] instanceof Method) {
                        Method method = (Method)os[1];
                        if ("get".equals(method.getName().substring(0, 3))) {
                            valType = method.getReturnType();
                        } else if ("set".equals(method.getName().substring(0, 3))) {
                            valType = ((Method)os[1]).getParameterTypes()[0];
                        }
                    }
                    String mthodName;
                    try {
                        if (valType == String.class) {
                            mthodName = String.valueOf(val.toString());
                            if (StringUtils.endsWith(mthodName, ".0")) {
                                val = StringUtils.substringBefore(mthodName, ".0");
                            } else {
                                val = String.valueOf(val.toString());
                            }
                        } else if (valType == Integer.class) {
                            val = Double.valueOf(val.toString()).intValue();
                        } else if (valType == Long.class) {
                            val = Double.valueOf(val.toString()).longValue();
                        } else if (valType == Double.class) {
                            val = Double.valueOf(val.toString());
                        } else if (valType == Float.class) {
                            val = Float.valueOf(val.toString());
                        } else if (valType == Date.class) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
                            SimpleDateFormat sdf3 = new SimpleDateFormat("MM/dd/yyyy");

                            try {
                                val = sdf.parse(val.toString());
                            } catch (ParseException var22) {
                                Date tmpDate = sdf2.parse(val.toString());
                                if (tmpDate.getTime() > 0L) {
                                    val = tmpDate;
                                } else {
                                    val = sdf3.parse(val.toString());
                                }
                            }
                        } else if (valType == User.class) {
                            val = UserUtils.getByLoginName(val.toString());
                        } else if (ef.fieldType() != Class.class) {
                            val = ef.fieldType().getMethod("getValue", String.class).invoke((Object)null, val.toString());
                        } else {
                            val = Class.forName(cls.getClass().getName().replaceAll(cls.getClass().getSimpleName(), "fieldtype." + valType.getSimpleName() + "Type")).getMethod("getValue", String.class).invoke((Object)null, val.toString());
                        }
                    } catch (Exception var23) {
                        val = null;
                    }

                    if (os[1] instanceof Field) {
                        Reflections.invokeSetter(e, ((Field)os[1]).getName(), val);
                    } else if (os[1] instanceof Method) {
                        mthodName = ((Method)os[1]).getName();
                        if ("get".equals(mthodName.substring(0, 3))) {
                            mthodName = "set" + StringUtils.substringAfter(mthodName, "get");
                        }

                        Reflections.invokeMethod(e, mthodName, new Class[]{valType}, new Object[]{val});
                    }
                }
            }

            dataList.add(e);
        }
        return dataList;
    }
    private static Object getCellValue(Row row, int column) {
        Object val = "";
        try {
            Cell cell = row.getCell(column);
            if (cell != null) {
                if(cell.getStringCellValue()!=null){
                    val = cell.getStringCellValue();
                }else if(cell.getNumericCellValue()!=0.0){
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        double d = cell.getNumericCellValue();
                        Date date = HSSFDateUtil.getJavaDate(d);
                        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
                        val = dformat.format(date);
                    } else {
                        NumberFormat nf = NumberFormat.getInstance();
                        nf.setGroupingUsed(false);
                        val = nf.format(cell.getNumericCellValue());
                    }
                }
            }

            return val;
        } catch (Exception var9) {
            return val;
        }
    }

}
