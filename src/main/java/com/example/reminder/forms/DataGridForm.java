package com.example.reminder.forms;

import java.util.List;
import java.util.Objects;

public class DataGridForm {

  public List header;
  public List<List<Object>> rows;
  public List footer;

  public DataGridForm() {}

  public List getHeader() {
    return header;
  }

  public void setHeader(List header) {
    this.header = header;
  }

  public List<List<Object>> getRows() {
    return rows;
  }

  public void setRows(List<List<Object>> rows) {
    this.rows = rows;
  }

  public List getFooter() {
    return footer;
  }

  public void setFooter(List footer) {
    this.footer = footer;
  }

  @Override
  public int hashCode() {
    return Objects.hash(header, rows, footer);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    if (this == o) {
      return true;
    }
    DataGridForm other = (DataGridForm) o;
    return Objects.equals(header, other.getHeader())
        && Objects.equals(rows, other.getRows())
        && Objects.equals(footer, other.getFooter());
  }
}
