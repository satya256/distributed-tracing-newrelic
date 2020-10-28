package com.sample.distributedtracing.core;

import com.sample.distributedtracing.model.TraceModel;
import io.grpc.Context;
import io.opentelemetry.trace.Span;
import io.opentelemetry.trace.SpanContext;
import io.opentelemetry.trace.Status;
import io.opentelemetry.trace.TracingContextUtils;

/**
 * The type Trace span.
 */
public final class TraceSpan {

  /**
   * The Trace sdk.
   */
  private final transient TraceSdk traceSdk;

  /**
   * The Span.
   */
  private transient Span span;

  /**
   * Instantiates a new Trace span.
   *
   * @param traceSdk the trace sdk
   */
  public TraceSpan(final TraceSdk traceSdk) {
    this.traceSdk = traceSdk;
  }

  /**
   * Start.
   *
   * @param spanName   the span name
   * @param traceModel the trace model
   */
  public void start(final String spanName, final TraceModel traceModel) {
    final Span.Builder builder =
        traceSdk.getTracer()
            .spanBuilder(spanName)
            .setSpanKind(traceModel.getKind());

    if (traceModel.getRemoteParentContext() != null) {
      builder.setParent(traceModel.getRemoteParentContext());
    }

    if (traceModel.getParentSpanContext() != null) {
      builder.setParent(traceModel.getParentSpanContext());
    }

    traceModel
        .getAttributes()
        .forEach(builder::setAttribute);

    span = builder.startSpan();

    traceSdk.getStorage()
        .put(spanName + TraceConstants.TRACE_PARENT_DATA, new TraceModel(traceModel, this));
  }

  /**
   * Stop.
   */
  public void stop() {
    span.end();
  }

  /**
   * Sets status.
   *
   * @param status the status
   */
  public void setStatus(final Status status) {
    span.setStatus(status);
  }

  /**
   * Gets span context.
   *
   * @return the span context
   */
  public SpanContext getSpanContext() {
    return span.getContext();
  }

  /**
   * With span context.
   *
   * @param context the context
   * @return the context
   */
  public Context withSpan(final Context context) {
    return TracingContextUtils.withSpan(span, context);
  }
}
