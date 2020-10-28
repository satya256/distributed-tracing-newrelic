package com.sample.telemetrynewrelic.core;

import com.sample.telemetrynewrelic.model.TraceModel;

/**
 * The interface Storage.
 */
public interface Istorage {

  /**
   * Put.
   *
   * @param parentTraceKey the parent trace key
   * @param traceModel     the trace model
   */
  void put(final String parentTraceKey, final TraceModel traceModel);

  /**
   * Get trace model.
   *
   * @param parentTraceKey the parent trace key
   * @return the trace model
   */
  TraceModel get(final String parentTraceKey);
}
