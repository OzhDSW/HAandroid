package io.altenems.companion.android.common.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AppNotifChannelsTest {

    @Test
    fun `Given appCreatedChannels when checking for duplicates then none exist`() {
        assertEquals(
            appCreatedChannels.size,
            appCreatedChannels.toSet().size,
            "appCreatedChannels contains duplicate channel IDs",
        )
    }
}
