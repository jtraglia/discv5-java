package org.ethereum.beacon.discovery.suppliers;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ArbitrarySupplier;
import net.jqwik.api.Combinators;
import org.apache.tuweni.bytes.Bytes;
import org.ethereum.beacon.discovery.message.TalkRespMessage;

public class TalkRespMessageSupplier implements ArbitrarySupplier<TalkRespMessage> {
  @Override
  public Arbitrary<TalkRespMessage> get() {
    Arbitrary<Bytes> requestId =
        Arbitraries.bytes().array(byte[].class).ofMaxSize(8).map(Bytes::of);
    Arbitrary<Bytes> response = Arbitraries.bytes().map(Bytes::of);
    return Combinators.combine(requestId, response).as(TalkRespMessage::new);
  }
}
