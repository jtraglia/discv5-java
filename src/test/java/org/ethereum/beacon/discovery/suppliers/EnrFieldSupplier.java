package org.ethereum.beacon.discovery.suppliers;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ArbitrarySupplier;
import org.ethereum.beacon.discovery.schema.EnrField;
import org.ethereum.beacon.discovery.schema.IdentitySchema;

public class EnrFieldSupplier implements ArbitrarySupplier<EnrField> {
  private static final BytesSupplier BYTES_SUPPLIER = new BytesSupplier();

  @Override
  public Arbitrary<EnrField> get() {
    Arbitrary<String> name =
        Arbitraries.of(
            EnrField.ID,
            EnrField.TCP,
            EnrField.UDP,
            EnrField.IP_V6,
            EnrField.IP_V4,
            EnrField.PKEY_SECP256K1,
            EnrField.TCP_V6,
            EnrField.UDP_V6);

    return name.map(
        (n) -> {
          switch (n) {
            case EnrField.ID:
              return new EnrField(n, IdentitySchema.V4);
            case EnrField.TCP:
            case EnrField.TCP_V6:
            case EnrField.UDP:
            case EnrField.UDP_V6:
              return new EnrField(n, Arbitraries.integers().sample());
            case EnrField.IP_V4:
            case EnrField.IP_V6:
            case EnrField.PKEY_SECP256K1:
            default:
              return new EnrField(n, BYTES_SUPPLIER.get().sample());
          }
        });
  }
}
