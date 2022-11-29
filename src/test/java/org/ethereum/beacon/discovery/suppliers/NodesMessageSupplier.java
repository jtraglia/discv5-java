package org.ethereum.beacon.discovery.suppliers;

import java.util.List;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ArbitrarySupplier;
import net.jqwik.api.Combinators;
import org.apache.tuweni.bytes.Bytes;
import org.ethereum.beacon.discovery.message.NodesMessage;
import org.ethereum.beacon.discovery.schema.NodeRecord;

public class NodesMessageSupplier implements ArbitrarySupplier<NodesMessage> {
  private static NodeRecordSupplier nodeRecordSupplier = new NodeRecordSupplier();

  @Override
  public Arbitrary<NodesMessage> get() {
    Arbitrary<Bytes> requestId =
        Arbitraries.bytes().array(byte[].class).ofMaxSize(8).map(Bytes::of);
    Arbitrary<Integer> total = Arbitraries.integers().greaterOrEqual(1);
    Arbitrary<List<NodeRecord>> records = nodeRecordSupplier.get().list();
    return Combinators.combine(requestId, total, records).as(NodesMessage::new);
  }
}
