/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.ethereum.beacon.discovery.message;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.ethereum.beacon.discovery.TestUtil.assertRejectTrailingBytes;
import static org.ethereum.beacon.discovery.TestUtil.assertRoundTrip;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.units.bigints.UInt64;
import org.ethereum.beacon.discovery.schema.NodeRecordFactory;
import org.ethereum.beacon.discovery.suppliers.PongMessageSupplier;
import org.ethereum.beacon.discovery.util.RlpDecodeException;
import org.junit.jupiter.api.Test;

class PongMessageTest {
  private static final DiscoveryV5MessageDecoder DECODER =
      new DiscoveryV5MessageDecoder(NodeRecordFactory.DEFAULT);

  @Property
  void shouldRoundTrip(@ForAll(supplier = PongMessageSupplier.class) final PongMessage message) {
    assertRoundTrip(DECODER, message);
  }

  @Property
  void shouldRejectTrailingBytes(
      @ForAll(supplier = PongMessageSupplier.class) final PongMessage message) {
    assertRejectTrailingBytes(DECODER, message);
  }

  @Test
  void shouldFailDecodingWhenIpHasIncorrectNumberOfBytes() {
    final PongMessage original =
        new PongMessage(
            Bytes.fromHexString("0x85482293"),
            UInt64.MAX_VALUE.subtract(1),
            Bytes.fromHexString("0x12"),
            48);

    final Bytes rlp = original.getBytes();
    assertThatThrownBy(() -> DECODER.decode(rlp)).isInstanceOf(RlpDecodeException.class);
  }

  @Test
  void shouldFailDecodingWhenPortIsInvalid() {
    // The last 3 bytes is the important part. 0x010000 (65536) is UInt16.Max + 1.
    final Bytes rlp = Bytes.fromHexString("0x02d7848548229388fffffffffffffffe841212121283010000");
    assertThatThrownBy(() -> DECODER.decode(rlp))
        .isInstanceOf(RlpDecodeException.class)
        .hasMessageContaining("Invalid port number");
  }
}
