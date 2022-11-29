package org.ethereum.beacon.discovery.suppliers;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ArbitrarySupplier;
import net.jqwik.api.Combinators;
import org.apache.tuweni.bytes.Bytes;
import org.ethereum.beacon.discovery.message.TalkReqMessage;

public class TalkReqMessageSupplier implements ArbitrarySupplier<TalkReqMessage> {
  @Override
  public Arbitrary<TalkReqMessage> get() {
    Arbitrary<Bytes> requestId =
        Arbitraries.bytes().array(byte[].class).ofMaxSize(8).map(Bytes::of);
    Arbitrary<Bytes> protocol = Arbitraries.bytes().map(Bytes::of);
    Arbitrary<Bytes> request = Arbitraries.bytes().map(Bytes::of);
    return Combinators.combine(requestId, protocol, request).as(TalkReqMessage::new);
  }
}
