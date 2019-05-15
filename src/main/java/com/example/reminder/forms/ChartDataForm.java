package com.example.reminder.forms;

import java.util.Collection;
import java.util.Objects;

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

  @Override
  public int hashCode() {
    return Objects.hash(cols, rows);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    if (this == o) {
      return true;
    }
    ChartDataForm other = (ChartDataForm) o;
    return Objects.equals(cols, other.getCols())
        && Objects.equals(rows, other.getRows());
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

    @Override
    public int hashCode() {
      return Objects.hash(id, label, pattern, type);
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || this.getClass() != o.getClass()) {
        return false;
      }
      if (this == o) {
        return true;
      }
      Column other = (Column) o;
      return Objects.equals(id, other.getId())
          && Objects.equals(label, other.getLabel())
          && Objects.equals(pattern, other.getPattern())
          && Objects.equals(type, other.getType());
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

    @Override
    public int hashCode() {
      return Objects.hash(c);
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || this.getClass() != o.getClass()) {
        return false;
      }
      if (this == o) {
        return true;
      }
      return Objects.equals(c, ((Row) o).getC());
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

      @Override
      public int hashCode() {
        return Objects.hash(v, f);
      }

      @Override
      public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
          return false;
        }
        if (this == o) {
          return true;
        }
        Data other = (Data) o;
        return Objects.equals(v, other.getV())
            && Objects.equals(f, other.getF());
      }

    }
  }
}
