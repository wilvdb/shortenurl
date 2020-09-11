package com.github.urlshorten.junit5;

import com.github.urlshorten.IdStrategy;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

class TestedUrlAggregator implements ArgumentsAggregator {
  @Override
  public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context)
      throws ArgumentsAggregationException {
    return new TestedUrl(
        accessor.getString(0), accessor.getString(1), IdStrategy.valueOf(accessor.getString(2)));
  }
}
