package tech.simter.jpa.ext;

import org.junit.Test;
import tech.simter.persistence.Sex;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author RJ 2017-04-28
 */
public class SexConverterTest {
  @Test
  public void success() throws Exception {
    SexConverter c = new SexConverter();
    for (Sex sex : Sex.values()) {
      assertThat(c.convertToDatabaseColumn(sex), is(sex.value()));
      assertThat(c.convertToEntityAttribute(sex.value()), is(sex));
    }
  }

  @Test
  public void convertNullAttributeValue() throws Exception {
    assertThat(new SexConverter().convertToDatabaseColumn(null), nullValue());
  }

  @Test(expected = NullPointerException.class)
  public void convertNullDbValue() throws Exception {
    new SexConverter().convertToEntityAttribute(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void convertUnsupportedDbValue() throws Exception {
    new SexConverter().convertToEntityAttribute(9999);
  }
}