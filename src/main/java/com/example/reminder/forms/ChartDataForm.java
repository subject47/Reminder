package com.example.reminder.forms;

import java.util.Collection;

/**
 * Class containing data for chart
 */
public class ChartDataForm {
  private Collection<Column> cols;
  private Collection<Row> rows;

  public ChartDataForm(Collection<Column> cols, Collection<Row> rows) {
    this.cols = cols;
    this.rows = rows;
  }

  public Collection<Column> getCols() {
    return cols;
  }

  public void setCols(Collection<Column> cols) {
    this.cols = cols;
  }

  public Collection<Row> getRows() {
    return rows;
  }

  public void setRows(Collection<Row> rows) {
    this.rows = rows;
  }



  public static class Column {
    private String id = "";
    private String label = "";
    private String pattern = "";
    private String type = "";

    public Column(String label, String type) {
      this.label = label;
      this.type = type;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getLabel() {
      return label;
    }

    public void setLabel(String label) {
      this.label = label;
    }

    public String getPattern() {
      return pattern;
    }

    public void setPattern(String pattern) {
      this.pattern = pattern;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }
  }



  public static class Row {
    private Collection<Data> c;

    public Row(Collection<Data> c) {
      this.c = c;
    }

    public Collection<Data> getC() {
      return c;
    }

    public void setC(Collection<Data> c) {
      this.c = c;
    }



    public static class Data {
      private Object v;
      private Object f;

      public Data(Object v) {
        this.v = v;
      }

      public Object getV() {
        return v;
      }

      public void setV(Object v) {
        this.v = v;
      }

      public Object getF() {
        return f;
      }

      public void setF(Object f) {
        this.f = f;
      }
    }
  }
}
