package org.ethereum.beacon.discovery.suppliers;

import java.util.List;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ArbitrarySupplier;
import net.jqwik.api.Combinators;
import org.apache.tuweni.bytes.Bytes;
import org.ethereum.beacon.discovery.message.FindNodeMessage;

public class FindNodeMessageSupplier implements ArbitrarySupplier<FindNodeMessage> {
  @Override
  public Arbitrary<FindNodeMessage> get() {
    Arbitrary<Bytes> requestId =
        Arbitraries.bytes().array(byte[].class).ofMaxSize(8).map(Bytes::of);
    Arbitrary<List<Integer>> distances = Arbitraries.integers().between(0, 256).list().ofMinSize(1);
    return Combinators.combine(requestId, distances).as(FindNodeMessage::new);
  }
}
