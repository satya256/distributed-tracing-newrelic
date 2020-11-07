package com.sample.telemetrynewrelic.core;

import com.sample.telemetrynewrelic.newrelic.SpanProcessorUtil;
import io.opentelemetry.exporters.logging.LoggingSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.MultiSpanProcessor;
import io.opentelemetry.sdk.trace.Sampler;
import io.opentelemetry.sdk.trace.TracerSdkProvider;
import io.opentelemetry.sdk.trace.config.TraceConfig;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.trace.Tracer;
import java.util.Arrays;
import java.util.Objects;

/**
 * The type Trace sdk.
 */
public final class TraceSdk {

  /**
   * The Tracer.
   */
  private transient Tracer tracer;

  /**
   * The Sampler.
   */
  private final transient Sampler sampler;

  /**
   * The New relic api key.
   */
  private final transient String newRelicApiKey;

  /**
   * The New relic service name.
   */
  private final transient String newRelicServiceName;

  /**
   * The Instrumentation name.
   */
  private final transient String instrumentationName;

  /**
   * The Instrumentation version.
   */
  private final transient String instrumentationVersion;

  /**
   * The Storage.
   */
  private final transient Istorage storage;

  /**
   * The constant MUTEX.
   */
  private static final Object MUTEX = new Object();

  /**
   * The constant instance.
   */
  private static volatile TraceSdk instance;

  /**
   * Build instance trace sdk.
   *
   * @param sampler                the sampler
   * @param newRelicApiKey         the new relic api key
   * @param newRelicServiceName    the new relic service name
   * @param instrumentationName    the instrumentation name
   * @param instrumentationVersion the instrumentation version
   * @param storage                the storage
   * @return the trace sdk
   */
  public static TraceSdk buildInstance(final Sampler sampler, final String newRelicApiKey,
                                       final String newRelicServiceName, final String instrumentationName,
                                       final String instrumentationVersion, final Istorage storage) {
    if (instance == null) {
      synchronized (MUTEX) {
        if (instance == null) {
          instance =
              new TraceSdk(sampler, newRelicApiKey, newRelicServiceName,
                  instrumentationName, instrumentationVersion, storage);
          instance.postProcess();
        }
      }
    }

    return instance;
  }

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static TraceSdk getInstance() {
    if (instance == null) {
      throw new UnsupportedOperationException("TraceSdk instance is not yet build");
    }

    return instance;
  }

  /**
   * Instantiates a new Trace sdk.
   *
   * @param sampler                the sampler
   * @param newRelicApiKey         the new relic api key
   * @param newRelicServiceName    the new relic service name
   * @param instrumentationName    the instrumentation name
   * @param instrumentationVersion the instrumentation version
   * @param storage                the storage
   */
  private TraceSdk(final Sampler sampler, final String newRelicApiKey, final String newRelicServiceName,
                   final String instrumentationName,
                   final String instrumentationVersion,
                   final Istorage storage) {
    Objects.requireNonNull(newRelicApiKey);
    Objects.requireNonNull(newRelicServiceName);
    Objects.requireNonNull(instrumentationName);
    Objects.requireNonNull(instrumentationVersion);
    Objects.requireNonNull(sampler);

    this.sampler = sampler;
    this.newRelicApiKey = newRelicApiKey;
    this.newRelicServiceName = newRelicServiceName;
    this.instrumentationName = instrumentationName;
    this.instrumentationVersion = instrumentationVersion;
    this.storage = storage;
  }

  /**
   * Post process.
   */
  private void postProcess() {
    final TraceConfig.Builder builder = TraceConfig.getDefault().toBuilder();
    builder.setSampler(sampler);

    final TracerSdkProvider tracerSdkProvider = OpenTelemetrySdk.getTracerProvider();
    tracerSdkProvider.updateActiveTraceConfig(builder.build());
    tracerSdkProvider
        .addSpanProcessor(
            MultiSpanProcessor.create(Arrays.asList(
                SpanProcessorUtil.build(newRelicApiKey, newRelicServiceName),
                SimpleSpanProcessor.newBuilder(new LoggingSpanExporter()).build())));

    tracer = tracerSdkProvider.get(instrumentationName, instrumentationVersion);
  }

  /**
   * Gets tracer.
   *
   * @return the tracer
   */
  public Tracer getTracer() {
    return tracer;
  }

  /**
   * Gets storage.
   *
   * @return the storage
   */
  public Istorage getStorage() {
    return storage;
  }
}
