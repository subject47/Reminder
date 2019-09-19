package com.budget.spendings.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;
import com.budget.spendings.domain.Category;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Component
public class CategoryCache {

  private Map<Integer, Category> cache;

  public void add(Category category) {
    if (isEmpty()) {
      cache = Maps.newHashMap();
    }
    cache.put(category.getId(), category);
  }

  public List<Category> listAll() {
    return Lists.newArrayList(cache.values());
  }

  public boolean isEmpty() {
    return MapUtils.isEmpty(cache);
  }

  public void addAll(Collection<Category> categories) {
    categories.stream().forEach(this::add);
  }

}
