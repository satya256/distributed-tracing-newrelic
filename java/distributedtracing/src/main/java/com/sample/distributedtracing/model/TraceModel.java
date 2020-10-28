package com.sample.distributedtracing.model;

import com.sample.distributedtracing.core.TraceSpan;
import io.grpc.Context;
import io.opentelemetry.trace.Link;
import io.opentelemetry.trace.Span;
import io.opentelemetry.trace.SpanContext;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The type Trace model.
 */
public class TraceModel {

  /**
   * The Parent span context.
   */
  private transient SpanContext parentSpanContext;

  /**
   * The Links.
   */
  private transient List<Link> links;

  /**
   * The Attributes.
   */
  private final Map<String, String> attributes;

  /**
   * The Kind.
   */
  private final Span.Kind kind;

  /**
   * The Trace span.
   */
  private transient TraceSpan traceSpan;

  /**
   * The Remote parent context.
   */
  private transient Context remoteParentContext;

  /**
   * Instantiates a new Trace model.
   *
   * @param kind              the kind
   * @param parentSpanContext the parent span context
   * @param attributes        the attributes
   * @param links             the links
   */
  public TraceModel(final Span.Kind kind,
                    final SpanContext parentSpanContext,
                    final Map<String, String> attributes,
                    final List<Link> links) {
    Objects.requireNonNull(kind);

    this.parentSpanContext = parentSpanContext;
    this.links = links;
    this.attributes = attributes;
    this.kind = kind;
  }

  /**
   * Instantiates a new Trace model.
   *
   * @param kind              the kind
   * @param parentSpanContext the parent span context
   * @param attributes        the attributes
   */
  public TraceModel(final Span.Kind kind,
                    final SpanContext parentSpanContext,
                    final Map<String, String> attributes) {
    Objects.requireNonNull(kind);

    this.parentSpanContext = parentSpanContext;
    this.attributes = attributes;
    this.kind = kind;
  }

  /**
   * Instantiates a new Trace model.
   *
   * @param kind                the kind
   * @param attributes          the attributes
   * @param links               the links
   * @param remoteParentContext the remote parent context
   */
  public TraceModel(final Span.Kind kind,
                    final Map<String, String> attributes,
                    final List<Link> links, final Context remoteParentContext) {
    Objects.requireNonNull(kind);
    Objects.requireNonNull(remoteParentContext);

    this.links = links;
    this.attributes = attributes;
    this.kind = kind;
    this.remoteParentContext = remoteParentContext;
  }

  /**
   * Instantiates a new Trace model.
   *
   * @param dataModel the data model
   * @param traceSpan the trace span
   */
  public TraceModel(final TraceModel dataModel, final TraceSpan traceSpan) {
    Objects.requireNonNull(traceSpan);

    this.parentSpanContext = dataModel.parentSpanContext;
    this.links = dataModel.links;
    this.traceSpan = traceSpan;
    this.attributes = dataModel.attributes;
    this.kind = dataModel.kind;
    this.remoteParentContext = dataModel.remoteParentContext;
  }

  /**
   * Instantiates a new Trace model.
   *
   * @param dataModel     the data model
   * @param parentContext the parent context
   */
  public TraceModel(final TraceModel dataModel, final Context parentContext) {
    Objects.requireNonNull(parentContext);

    this.parentSpanContext = dataModel.parentSpanContext;
    this.links = dataModel.links;
    this.traceSpan = dataModel.traceSpan;
    this.attributes = dataModel.attributes;
    this.kind = dataModel.kind;
    this.remoteParentContext = parentContext;
  }


  /**
   * Gets parent span context.
   *
   * @return the parent span context
   */
  public SpanContext getParentSpanContext() {
    return parentSpanContext;
  }

  /**
   * Gets links.
   *
   * @return the links
   */
  public List<Link> getLinks() {
    return links;
  }

  /**
   * Gets attributes.
   *
   * @return the attributes
   */
  public Map<String, String> getAttributes() {
    return attributes;
  }

  /**
   * Gets trace span.
   *
   * @return the trace span
   */
  public TraceSpan getTraceSpan() {
    return traceSpan;
  }

  /**
   * Gets kind.
   *
   * @return the kind
   */
  public Span.Kind getKind() {
    return kind;
  }

  /**
   * Gets remote parent context.
   *
   * @return the remote parent context
   */
  public Context getRemoteParentContext() {
    return remoteParentContext;
  }
}
