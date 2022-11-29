/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.ethereum.beacon.discovery.message;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.ethereum.beacon.discovery.TestUtil.assertRejectTrailingBytes;
import static org.ethereum.beacon.discovery.TestUtil.assertRoundTrip;

import java.util.List;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.apache.tuweni.bytes.Bytes;
import org.ethereum.beacon.discovery.SimpleIdentitySchemaInterpreter;
import org.ethereum.beacon.discovery.schema.NodeRecordFactory;
import org.ethereum.beacon.discovery.suppliers.FindNodeMessageSupplier;
import org.ethereum.beacon.discovery.util.RlpDecodeException;
import org.junit.jupiter.api.Test;

class FindNodeMessageTest {
  private static final DiscoveryV5MessageDecoder DECODER =
      new DiscoveryV5MessageDecoder(new NodeRecordFactory(new SimpleIdentitySchemaInterpreter()));

  @Property
  void shouldRoundTrip(
      @ForAll(supplier = FindNodeMessageSupplier.class) final FindNodeMessage message) {
    assertRoundTrip(DECODER, message);
  }

  @Property
  void shouldRejectTrailingBytes(
      @ForAll(supplier = FindNodeMessageSupplier.class) final FindNodeMessage message) {
    assertRejectTrailingBytes(DECODER, message);
  }

  @Test
  void shouldRejectInvalidDistance() {
    final FindNodeMessage message =
        new FindNodeMessage(Bytes.fromHexString("0x134488556699"), List.of(257));
    final Bytes rlp = message.getBytes();
    assertThatThrownBy(() -> DECODER.decode(rlp))
        .isInstanceOf(RlpDecodeException.class)
        .hasMessageContaining("Invalid distance");
  }
}
