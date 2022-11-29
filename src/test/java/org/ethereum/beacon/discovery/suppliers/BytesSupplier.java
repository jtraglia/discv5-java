package org.ethereum.beacon.discovery.suppliers;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ArbitrarySupplier;
import org.apache.tuweni.bytes.Bytes;

public class BytesSupplier implements ArbitrarySupplier<Bytes> {
  @Override
  public Arbitrary<Bytes> get() {
    return Arbitraries.bytes().map(Bytes::of);
  }
}
