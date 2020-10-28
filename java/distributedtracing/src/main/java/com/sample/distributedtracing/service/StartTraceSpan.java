package com.sample.distributedtracing.service;

import com.sample.distributedtracing.core.TraceSdk;
import com.sample.distributedtracing.core.TraceSpan;
import com.sample.distributedtracing.model.TraceModel;
import io.opentelemetry.trace.Span;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * The type Start trace span.
 */
public final class StartTraceSpan {

  /**
   * Start internal.
   *
   * @param traceModel the trace model
   * @param traceName  the trace name
   */
  private void startInternal(final TraceModel traceModel, final String traceName) {
    Objects.requireNonNull(traceModel);
    Objects.requireNonNull(traceName);

    final TraceSpan traceSpan = new TraceSpan(TraceSdk.getInstance());
    traceSpan.start(traceName, traceModel);
  }

  /**
   * Start.
   *
   * @param traceName the trace name
   */
  public void start(final String traceName) {
    Objects.requireNonNull(traceName);

    final TraceModel traceModel =
        new TraceModel(Span.Kind.INTERNAL, null, Collections.emptyMap());

    startInternal(traceModel, traceName);
  }

  /**
   * Start.
   *
   * @param traceName  the trace name
   * @param attributes the attributes
   */
  public void start(final String traceName, final Map<String, String> attributes) {
    Objects.requireNonNull(traceName);
    Objects.requireNonNull(attributes);

    final TraceModel traceModel =
        new TraceModel(Span.Kind.INTERNAL, null, attributes);

    startInternal(traceModel, traceName);
  }
}
