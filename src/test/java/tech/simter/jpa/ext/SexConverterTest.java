package tech.simter.jpa.ext;

import org.junit.jupiter.api.Test;
import tech.simter.persistence.Sex;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author RJ
 */
class SexConverterTest {
  @Test
  void success() {
    SexConverter c = new SexConverter();
    for (Sex sex : Sex.values()) {
      assertEquals(sex.value(), c.convertToDatabaseColumn(sex));
      assertEquals(sex, c.convertToEntityAttribute(sex.value()));
    }
  }

  @Test
  void convertNullAttributeValue() {
    assertNull(new SexConverter().convertToDatabaseColumn(null));
  }

  @Test
  void convertNullDbValue() {
    assertThrows(NullPointerException.class, () -> new SexConverter().convertToEntityAttribute(null));
  }

  @Test
  void convertUnsupportedDbValue() {
    assertThrows(IllegalArgumentException.class, () -> new SexConverter().convertToEntityAttribute(9999));
  }
}