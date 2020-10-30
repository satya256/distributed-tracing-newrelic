package com.sample.telemetrynewrelic.service;

import com.sample.telemetrynewrelic.core.TraceSdk;
import com.sample.telemetrynewrelic.core.TraceSpan;
import com.sample.telemetrynewrelic.model.TraceModel;
import com.sample.telemetrynewrelic.utils.ExtractTraceContext;
import io.opentelemetry.trace.Span;
import io.opentelemetry.trace.SpanContext;
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
  private static void startInternal(final TraceModel traceModel,
                                    final String traceName) {
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
  public static void start(final String traceName) {
    Objects.requireNonNull(traceName);

    final TraceModel traceModel =
        new TraceModel(Span.Kind.INTERNAL, null, Collections.emptyMap());

    startInternal(traceModel, traceName);
  }

  /**
   * Start.
   *
   * @param traceName       the trace name
   * @param parentTraceName the parent trace name
   */
  public static void start(final String traceName, final String parentTraceName) {
    Objects.requireNonNull(traceName);
    Objects.requireNonNull(parentTraceName);

    final SpanContext spanContext = TraceSdk.getInstance().getStorage().get(parentTraceName).getParentSpanContext();
    final TraceModel traceModel =
        new TraceModel(Span.Kind.INTERNAL, spanContext, Collections.emptyMap());

    startInternal(traceModel, traceName);
  }

  /**
   * Start.
   *
   * @param traceName  the trace name
   * @param attributes the attributes
   */
  public static void start(final String traceName, final Map<String, String> attributes) {
    Objects.requireNonNull(traceName);
    Objects.requireNonNull(attributes);

    final TraceModel traceModel =
        new TraceModel(Span.Kind.INTERNAL, null, attributes);

    startInternal(traceModel, traceName);
  }

  /**
   * Start.
   *
   * @param traceName       the trace name
   * @param parentTraceName the parent trace name
   * @param attributes      the attributes
   */
  public static void start(final String traceName,
                           final String parentTraceName,
                           final Map<String, String> attributes) {
    Objects.requireNonNull(traceName);
    Objects.requireNonNull(parentTraceName);
    Objects.requireNonNull(attributes);

    final SpanContext spanContext = TraceSdk.getInstance().getStorage().get(parentTraceName).getParentSpanContext();
    final TraceModel traceModel =
        new TraceModel(Span.Kind.INTERNAL, spanContext, attributes);

    startInternal(traceModel, traceName);
  }

  /**
   * Start.
   *
   * @param traceName                the trace name
   * @param underRemoteParentContext the under remote parent context
   */
  public static void start(final String traceName, final boolean underRemoteParentContext) {
    Objects.requireNonNull(traceName);

    if (underRemoteParentContext) {
      final TraceModel traceModel =
          new TraceModel(Span.Kind.INTERNAL, Collections.emptyMap(), null, ExtractTraceContext.extract());

      startInternal(traceModel, traceName);
    }
  }
}
