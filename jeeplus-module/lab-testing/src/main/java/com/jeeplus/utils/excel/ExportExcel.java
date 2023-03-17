package com.jeeplus.utils.excel;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.Reflections;
import com.jeeplus.sys.utils.DictUtils;
import com.jeeplus.utils.excel.annotation.ExcelField;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.*;

public class ExportExcel {
    private static Logger log = LoggerFactory.getLogger(ExportExcel.class);
    private SXSSFWorkbook wb;
    private SXSSFSheet sheet;
    private Map<String, CellStyle> styles;
    private int rownum;
    List<Object[]> annotationList;

    public ExportExcel(String title, Class<?> cls) {
        this(title, cls, 1);
    }

    public ExportExcel(String title, Class<?> cls, int type, int... groups) {
        this(title, cls, 1, new String[]{"系统导入失败说明"}, groups);
    }

    public ExportExcel(String title, Class<?> cls, int type, String[] ignoreColumn, int... groups) {
        this.annotationList = Lists.newArrayList();
        Field[] fs = cls.getDeclaredFields();
        Field[] var10 = fs;
        int var9 = fs.length;

        int g;
        int var15;
        int efg;
        int var19;
        for(int var8 = 0; var8 < var9; ++var8) {
            Field f = var10[var8];
            ExcelField ef = (ExcelField)f.getAnnotation(ExcelField.class);
            if (ef != null && (ef.type() == 0 || ef.type() == type)) {
                if (groups != null && groups.length > 0) {
                    boolean inGroup = false;
                    int[] var16 = groups;
                    var15 = groups.length;

                    for(g = 0; g < var15; ++g) {
                        int g2 = var16[g];
                        if (inGroup) {
                            break;
                        }

                        int[] var20;
                        var19 = (var20 = ef.groups()).length;

                        for(efg = 0; efg < var19; ++efg) {
                            int efg2 = var20[efg];
                            if (g2 == efg2) {
                                inGroup = true;
                                this.annotationList.add(new Object[]{ef, f});
                                break;
                            }
                        }
                    }
                } else {
                    this.annotationList.add(new Object[]{ef, f});
                }
            }
        }

        Method[] ms = cls.getDeclaredMethods();
        Method[] var31 = ms;
        int var27 = ms.length;

        for(var9 = 0; var9 < var27; ++var9) {
            Method m = var31[var9];
            ExcelField ef = (ExcelField)m.getAnnotation(ExcelField.class);
            if (ef != null && (ef.type() == 0 || ef.type() == type)) {
                if (groups != null && groups.length > 0) {
                    boolean inGroup = false;
                    int[] var37 = groups;
                    int var36 = groups.length;

                    for(var15 = 0; var15 < var36; ++var15) {
                        g = var37[var15];
                        if (inGroup) {
                            break;
                        }

                        int[] var21;
                        int var38 = (var21 = ef.groups()).length;

                        for(var19 = 0; var19 < var38; ++var19) {
                            efg = var21[var19];
                            if (g == efg) {
                                inGroup = true;
                                this.annotationList.add(new Object[]{ef, m});
                                break;
                            }
                        }
                    }
                } else {
                    this.annotationList.add(new Object[]{ef, m});
                }
            }
        }

        Collections.sort(this.annotationList, new Comparator<Object[]>() {
            public int compare(Object[] o1, Object[] o2) {
                return (new Integer(((ExcelField)o1[0]).sort())).compareTo(new Integer(((ExcelField)o2[0]).sort()));
            }
        });
        if (ignoreColumn != null && ignoreColumn.length > 0) {
            Iterator<Object[]> tmpIt = this.annotationList.iterator();
            List ignoreColumnList = Arrays.asList(ignoreColumn);

            while(tmpIt.hasNext()) {
                Object[] objects = (Object[])tmpIt.next();
                if (ignoreColumnList.contains(((ExcelField)objects[0]).title())) {
                    tmpIt.remove();
                }
            }
        }

        List<String> headerList = Lists.newArrayList();

        String t;
        for(Iterator var30 = this.annotationList.iterator(); var30.hasNext(); headerList.add(t)) {
            Object[] os = (Object[])var30.next();
            t = ((ExcelField)os[0]).title();
            if (type == 1) {
                String[] ss = StringUtils.split(t, "**", 2);
                if (ss.length == 2) {
                    t = ss[0];
                }
            }
        }

        this.initialize(title, headerList);
    }

    public ExportExcel(String title, String[] headers) {
        this.annotationList = Lists.newArrayList();
        this.initialize(title, Lists.newArrayList(headers));
    }

    public ExportExcel(String title, List<String> headerList) {
        this.annotationList = Lists.newArrayList();
        this.initialize(title, headerList);
    }

    private void initialize(String title, List<String> headerList) {
        this.wb = new SXSSFWorkbook(500);
        this.sheet = this.wb.createSheet("Export");
        this.styles = this.createStyles(this.wb);
        SXSSFRow headerRow;
        if (StringUtils.isNotBlank(title)) {
            headerRow = this.sheet.createRow(this.rownum++);
            headerRow.setHeightInPoints(30.0F);
            Cell titleCell = headerRow.createCell(0);
            titleCell.setCellStyle((CellStyle)this.styles.get("title"));
            titleCell.setCellValue(title);
            this.sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), headerRow.getRowNum(), headerList.size() - 1));
        }

        if (headerList == null) {
            throw new RuntimeException("headerList not null!");
        } else {
            headerRow = this.sheet.createRow(this.rownum++);
            headerRow.setHeightInPoints(16.0F);

            int i;
            for(i = 0; i < headerList.size(); ++i) {
                Cell cell = headerRow.createCell(i);
                cell.setCellStyle((CellStyle)this.styles.get("header"));
                String[] ss = StringUtils.split((String)headerList.get(i), "**", 2);
                if (ss.length == 2) {
                    cell.setCellValue(ss[0]);
                    Comment comment = this.sheet.createDrawingPatriarch().createCellComment(new XSSFClientAnchor(0, 0, 0, 0, 3, 3, 5, 6));
                    comment.setString(new XSSFRichTextString(ss[1]));
                    cell.setCellComment(comment);
                } else {
                    cell.setCellValue((String)headerList.get(i));
                }

                this.sheet.trackAllColumnsForAutoSizing();
                this.sheet.autoSizeColumn(i);
            }

            for(i = 0; i < headerList.size(); ++i) {
                int colWidth = this.sheet.getColumnWidth(i) * 2;
                this.sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
            }

            log.debug("Initialize success.");
        }
    }

    private Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap();
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font titleFont = wb.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short)16);
        titleFont.setBold(true);
        style.setFont(titleFont);
        styles.put("title", style);
        style = wb.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short)10);
        style.setFont(dataFont);
        styles.put("data", style);
        style = wb.createCellStyle();
        style.cloneStyleFrom((CellStyle)styles.get("data"));
        style.setAlignment(HorizontalAlignment.LEFT);
        styles.put("data1", style);
        style = wb.createCellStyle();
        style.cloneStyleFrom((CellStyle)styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        styles.put("data2", style);
        style = wb.createCellStyle();
        style.cloneStyleFrom((CellStyle)styles.get("data"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        styles.put("data3", style);
        style = wb.createCellStyle();
        style.cloneStyleFrom((CellStyle)styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short)10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("header", style);
        return styles;
    }

    public Row addRow() {
        return this.sheet.createRow(this.rownum++);
    }

    public Cell addCell(Row row, int column, Object val) {
        return this.addCell(row, column, val, 0, Class.class);
    }

    public Cell addCell(Row row, int column, Object val, int align, Class<?> fieldType) {
        Cell cell = row.createCell(column);
        CellStyle style = (CellStyle)this.styles.get("data_column_" + column);
        String cellFormatString = "@";

        try {
            if (val == null) {
                cell.setCellValue("");
            } else if (val instanceof String) {
                cell.setCellValue((String)val);
            } else if (val instanceof Integer) {
                cell.setCellValue((double)(Integer)val);
                cellFormatString = "0";
            } else if (val instanceof Long) {
                cell.setCellValue((double)(Long)val);
                cellFormatString = "0";
            } else if (val instanceof Double) {
                cell.setCellValue((Double)val);
                cellFormatString = "0.00";
            } else if (val instanceof Float) {
                cell.setCellValue((double)(Float)val);
                cellFormatString = "0.00";
            } else if (val instanceof Date) {
                cell.setCellValue((Date)val);
                cellFormatString = "yyyy-MM-dd";
            } else if (fieldType != Class.class) {
                cell.setCellValue((String)fieldType.getMethod("setValue", Object.class).invoke((Object)null, val));
            } else {
                cell.setCellValue((String)Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(), "fieldtype." + val.getClass().getSimpleName() + "Type")).getMethod("setValue", Object.class).invoke((Object)null, val));
            }

            if (val != null) {
                if (style == null) {
                    style = this.wb.createCellStyle();
                    style.cloneStyleFrom((CellStyle)this.styles.get("data" + (align >= 1 && align <= 3 ? align : "")));
                    style.setDataFormat(this.wb.createDataFormat().getFormat(cellFormatString));
                    this.styles.put("data_column_" + column, style);
                }

                cell.setCellStyle(style);
            }
        } catch (Exception var10) {
            log.info("Set cell value [" + row.getRowNum() + "," + column + "] error: " + var10.toString());
            cell.setCellValue(val.toString());
        }

        cell.setCellStyle(style);
        return cell;
    }

    public <E> ExportExcel setDataList(List<E> list) {
        Iterator var3 = list.iterator();

        while(var3.hasNext()) {
            E e = (E) var3.next();
            int colunm = 0;
            Row row = this.addRow();
            StringBuilder sb = new StringBuilder();
            Iterator var8 = this.annotationList.iterator();

            while(var8.hasNext()) {
                Object[] os = (Object[])var8.next();
                ExcelField ef = (ExcelField)os[0];
                Object val = null;

                try {
                    if (StringUtils.isNotBlank(ef.value())) {
                        val = Reflections.invokeGetter(e, ef.value());
                    } else if (os[1] instanceof Field) {
                        val = Reflections.invokeGetter(e, ((Field)os[1]).getName());
                    } else if (os[1] instanceof Method) {
                        val = Reflections.invokeMethod(e, ((Method)os[1]).getName(), new Class[0], new Object[0]);
                    }

                    if (StringUtils.isNotBlank(ef.dictType())) {
                        val = DictUtils.getDictLabel(val == null ? "" : val.toString(), ef.dictType(), "");
                    }
                } catch (Exception var12) {
                    log.info(var12.toString());
                    val = "";
                }

                this.addCell(row, colunm++, val, ef.align(), ef.fieldType());
                sb.append(val + ", ");
            }

//            log.debug("Write success: [" + row.getRowNum() + "] " + sb.toString());
        }

        return this;
    }

    public ExportExcel write(OutputStream os) throws IOException {
        this.wb.write(os);
        return this;
    }

    public ExportExcel write(HttpServletResponse response, String fileName) throws IOException {
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", URLEncoder.encode(fileName, "UTF8"));
        this.write(response.getOutputStream());
        return this;
    }

    public ExportExcel writeFile(String name) throws FileNotFoundException, IOException {
        FileOutputStream os = new FileOutputStream(name);
        this.write(os);
        return this;
    }

    public ExportExcel dispose() {
        this.wb.dispose();
        return this;
    }

    public byte[] excelByte() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        this.wb.write(baos);
        byte[] buff = new byte[2048];
        buff = baos.toByteArray();
        return buff;
    }
}
