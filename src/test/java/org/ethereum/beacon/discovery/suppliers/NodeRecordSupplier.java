package org.ethereum.beacon.discovery.suppliers;

import java.util.ArrayList;
import java.util.List;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ArbitrarySupplier;
import net.jqwik.api.Combinators;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.crypto.SECP256K1;
import org.apache.tuweni.units.bigints.UInt64;
import org.ethereum.beacon.discovery.schema.EnrField;
import org.ethereum.beacon.discovery.schema.IdentitySchema;
import org.ethereum.beacon.discovery.schema.IdentitySchemaV4Interpreter;
import org.ethereum.beacon.discovery.schema.NodeRecord;
import org.ethereum.beacon.discovery.util.Functions;

public class NodeRecordSupplier implements ArbitrarySupplier<NodeRecord> {
  private static IdentitySchemaV4Interpreter identitySchemaV4Interpreter =
      new IdentitySchemaV4Interpreter();
  private static EnrFieldSupplier enrFieldSupplier = new EnrFieldSupplier();
  private static SECP256K1.KeyPair keyPair = Functions.randomKeyPair();
  private static Bytes compressedPkey =
      Functions.deriveCompressedPublicKeyFromPrivate(keyPair.secretKey());

  @Override
  public Arbitrary<NodeRecord> get() {
    Arbitrary<IdentitySchemaV4Interpreter> interpreter =
        Arbitraries.of(identitySchemaV4Interpreter);
    Arbitrary<UInt64> seq =
        Arbitraries.bytes().array(byte[].class).ofSize(8).map(Bytes::of).map(UInt64::fromBytes);
    Arbitrary<EnrField> pkey =
        Arbitraries.just(new EnrField(EnrField.PKEY_SECP256K1, compressedPkey));
    Arbitrary<EnrField> id = Arbitraries.just(new EnrField(EnrField.ID, IdentitySchema.V4));
    Arbitrary<List<EnrField>> others = enrFieldSupplier.get().list();
    Arbitrary<List<EnrField>> fields =
        Combinators.combine(id, pkey, others)
            .as(
                (i, p, o) -> {
                  List<EnrField> all = new ArrayList<>();
                  all.add(i);
                  all.add(p);
                  all.addAll(o);
                  return all;
                });
    return Combinators.combine(interpreter, seq, fields).as(NodeRecord::fromValues);
  }
}
