package com.sample.telemetrynewrelic.newrelic;

import com.newrelic.telemetry.Attributes;
import com.newrelic.telemetry.opentelemetry.export.AttributeNames;
import com.newrelic.telemetry.opentelemetry.export.NewRelicSpanExporter;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;

/**
 * The type Span processor.
 */
public final class SpanProcessorUtil {

  /**
   * Build batch span processor.
   *
   * @param newRelicApiKey      the new relic api key
   * @param newRelicServiceName the new relic service name
   * @return the batch span processor
   */
  public static BatchSpanProcessor build(final String newRelicApiKey, final String newRelicServiceName) {
    final Attributes attributes = new Attributes().put(AttributeNames.SERVICE_NAME, newRelicServiceName);

    final SpanExporter spanExporter =
        NewRelicSpanExporter
            .newBuilder()
            .apiKey(newRelicApiKey)
            .commonAttributes(attributes)
            .build();

    return
        BatchSpanProcessor
            .newBuilder(spanExporter)
            .setMaxExportBatchSize(128)
            .setMaxQueueSize(16_384)
            .setExporterTimeoutMillis(60_000)
            .setScheduleDelayMillis(256)
            .build();
  }

  /**
   * Instantiates a new Span processor.
   */
  private SpanProcessorUtil() {
    //Do nothing
  }
}
