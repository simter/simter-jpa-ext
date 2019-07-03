package tech.simter.jpa.ext;

import org.junit.jupiter.api.Test;
import tech.simter.persistence.CommonState;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author RJ
 */
class CommonStateConverterTest {
  @Test
  void success() {
    CommonStateConverter c = new CommonStateConverter();
    for (CommonState state : CommonState.values()) {
      assertEquals(state.value(), c.convertToDatabaseColumn(state));
      assertEquals(state, c.convertToEntityAttribute(state.value()));
    }
  }

  @Test
  void convertNullAttributeValue() {
    assertNull(new CommonStateConverter().convertToDatabaseColumn(null));
  }

  @Test
  void convertNullDbValue() {
    assertThrows(NullPointerException.class, () -> new CommonStateConverter().convertToEntityAttribute(null));
  }

  @Test
  void convertUnsupportedDbValue() {
    assertThrows(IllegalArgumentException.class, () -> new CommonStateConverter().convertToEntityAttribute(9999));
  }
}