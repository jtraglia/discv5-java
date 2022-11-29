package org.ethereum.beacon.discovery.suppliers;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ArbitrarySupplier;
import net.jqwik.api.Combinators;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.units.bigints.UInt64;
import org.ethereum.beacon.discovery.message.PongMessage;

public class PongMessageSupplier implements ArbitrarySupplier<PongMessage> {
  @Override
  @SuppressWarnings("unchecked")
  public Arbitrary<PongMessage> get() {
    Arbitrary<Bytes> requestId =
        Arbitraries.bytes().array(byte[].class).ofMaxSize(8).map(Bytes::of);
    Arbitrary<UInt64> seq =
        Arbitraries.bytes().array(byte[].class).ofSize(8).map(Bytes::of).map(UInt64::fromBytes);
    Arbitrary<Bytes> recipientIp =
        Arbitraries.oneOf(
            Arbitraries.bytes().array(byte[].class).ofSize(4).map(Bytes::of),
            Arbitraries.bytes().array(byte[].class).ofSize(16).map(Bytes::of));
    Arbitrary<Integer> recipientPort = Arbitraries.integers().between(0, 65535);
    return Combinators.combine(requestId, seq, recipientIp, recipientPort).as(PongMessage::new);
  }
}
