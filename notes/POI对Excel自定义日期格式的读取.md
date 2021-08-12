# POI对Excel自定义日期格式的读取

**1、读取Excel**

```
private List<String[]> rosolveFile(InputStream is, String suffix,
      int startRow) throws IOException, FileNotFoundException {
    Workbook xssfWorkbook = null;
    if ("xls".equals(suffix)) {
      xssfWorkbook = new HSSFWorkbook(is);
    } else if ("xlsx".equals(suffix)) {
      xssfWorkbook = new XSSFWorkbook(is);
    }
    Sheet xssfSheet = xssfWorkbook.getSheetAt(0);
    if (xssfSheet == null) {
      return null;
    }
    ArrayList<String[]> list = new ArrayList<String[]>();
    int lastRowNum = xssfSheet.getLastRowNum();
    for (int rowNum = startRow; rowNum <= lastRowNum; rowNum++) {
      if (xssfSheet.getRow(rowNum) != null) {
        Row xssfRow = xssfSheet.getRow(rowNum);
        short firstCellNum = xssfRow.getFirstCellNum();
        short lastCellNum = xssfRow.getLastCellNum();
        if (firstCellNum != lastCellNum) {
          String[] values = new String[lastCellNum];
          for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
            Cell xssfCell = xssfRow.getCell(cellNum);
            if (xssfCell == null) {
              values[cellNum] = "";
            } else {
              values[cellNum] = parseExcel(xssfCell);
            }
          }
          list.add(values);
        }
      }
    }
    return list;
  }
```

**2、Excel数据处理：**

Excel存储日期、时间均以数值类型进行存储，读取时POI先判断是是否是数值类型，再进行判断转化

**1、数值格式\(CELL\_TYPE\_NUMERIC\):**

1.纯数值格式：getNumericCellValue\(\) 直接获取数据

2.日期格式：处理yyyy\-MM\-dd, d/m/yyyy h:mm, HH:mm 等不含文字的日期格式

1\).判断是否是日期格式：HSSFDateUtil.isCellDateFormatted\(cell\)

2\).判断是日期或者时间

cell.getCellStyle\(\).getDataFormat\(\) == HSSFDataFormat.getBuiltinFormat\("h:mm"\)

OR: cell.getCellStyle\(\).getDataFormat\(\) == HSSFDataFormat.getBuiltinFormat\("yyyy\-MM\-dd"\)

3.自定义日期格式：处理yyyy年m月d日,h时mm分,yyyy年m月等含文字的日期格式

判断cell.getCellStyle\(\).getDataFormat\(\)值，解析数值格式

yyyy年m月d日\-\-\-\-\-\>31

m月d日\-\-\-\-\>58

h时mm分\-\-\-\>32

**2、字符格式\(CELL\_TYPE\_STRING\):直接获取内容**

```
private String parseExcel(Cell cell) {
    String result = new String();
    switch (cell.getCellType()) {
    case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型
      if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
        SimpleDateFormat sdf = null;
        if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
            .getBuiltinFormat("h:mm")) {
          sdf = new SimpleDateFormat("HH:mm");
        } else {// 日期
          sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        Date date = cell.getDateCellValue();
        result = sdf.format(date);
      } else if (cell.getCellStyle().getDataFormat() == 58) {
        // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        double value = cell.getNumericCellValue();
        Date date = org.apache.poi.ss.usermodel.DateUtil
            .getJavaDate(value);
        result = sdf.format(date);
      } else {
        double value = cell.getNumericCellValue();
        CellStyle style = cell.getCellStyle();
        DecimalFormat format = new DecimalFormat();
        String temp = style.getDataFormatString();
        // 单元格设置成常规
        if (temp.equals("General")) {
          format.applyPattern("#");
        }
        result = format.format(value);
      }
      break;
    case HSSFCell.CELL_TYPE_STRING:// String类型
      result = cell.getRichStringCellValue().toString();
      break;
    case HSSFCell.CELL_TYPE_BLANK:
      result = "";
    default:
      result = "";
      break;
    }
    return result;
  }
```

**\*万能处理方案：**

所有日期格式都可以通过getDataFormat\(\)值来判断

yyyy\-MM\-dd\-\-\-\-\- 14

yyyy年m月d日\-\-\- 31

yyyy年m月\-\-\-\-\-\-\- 57

m月d日  \-\-\-\-\-\-\-\-\-\- 58

HH:mm\-\-\-\-\-\-\-\-\-\-\- 20

h时mm分  \-\-\-\-\-\-\- 32

```
//1、判断是否是数值格式
if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
  short format = cell.getCellStyle().getDataFormat();
  SimpleDateFormat sdf = null;
  if(format == 14 || format == 31 || format == 57 || format == 58){
    //日期
    sdf = new SimpleDateFormat("yyyy-MM-dd");
  }else if (format == 20 || format == 32) {
    //时间
    sdf = new SimpleDateFormat("HH:mm");
  }
  double value = cell.getNumericCellValue();
  Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
  result = sdf.format(date);
}
```
