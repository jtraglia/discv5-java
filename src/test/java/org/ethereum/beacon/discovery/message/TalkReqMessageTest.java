/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.ethereum.beacon.discovery.message;

import static org.ethereum.beacon.discovery.TestUtil.assertRejectTrailingBytes;
import static org.ethereum.beacon.discovery.TestUtil.assertRoundTrip;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.ethereum.beacon.discovery.schema.NodeRecordFactory;
import org.ethereum.beacon.discovery.suppliers.TalkReqMessageSupplier;

class TalkReqMessageTest {
  private static final DiscoveryV5MessageDecoder DECODER =
      new DiscoveryV5MessageDecoder(NodeRecordFactory.DEFAULT);

  @Property
  void shouldRoundTrip(
      @ForAll(supplier = TalkReqMessageSupplier.class) final TalkReqMessage message) {
    assertRoundTrip(DECODER, message);
  }

  @Property
  void shouldRejectTrailingBytes(
      @ForAll(supplier = TalkReqMessageSupplier.class) final TalkReqMessage message) {
    assertRejectTrailingBytes(DECODER, message);
  }
}
