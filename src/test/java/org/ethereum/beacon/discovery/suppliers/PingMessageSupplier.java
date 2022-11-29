package org.ethereum.beacon.discovery.suppliers;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ArbitrarySupplier;
import net.jqwik.api.Combinators;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.units.bigints.UInt64;
import org.ethereum.beacon.discovery.message.PingMessage;

public class PingMessageSupplier implements ArbitrarySupplier<PingMessage> {
  @Override
  public Arbitrary<PingMessage> get() {
    Arbitrary<Bytes> requestId =
        Arbitraries.bytes().array(byte[].class).ofMaxSize(8).map(Bytes::of);
    Arbitrary<UInt64> seq =
        Arbitraries.bytes().array(byte[].class).ofSize(8).map(Bytes::of).map(UInt64::fromBytes);
    return Combinators.combine(requestId, seq).as(PingMessage::new);
  }
}
