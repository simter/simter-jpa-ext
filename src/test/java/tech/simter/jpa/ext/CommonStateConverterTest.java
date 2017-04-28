package tech.simter.jpa.ext;

import org.junit.Test;
import tech.simter.persistence.CommonState;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author RJ 2017-04-28
 */
public class CommonStateConverterTest {
  @Test
  public void success() throws Exception {
    CommonStateConverter c = new CommonStateConverter();
    for (CommonState state : CommonState.values()) {
      assertThat(c.convertToDatabaseColumn(state), is(state.value()));
      assertThat(c.convertToEntityAttribute(state.value()), is(state));
    }
  }

  @Test
  public void convertNullAttributeValue() throws Exception {
    assertThat(new CommonStateConverter().convertToDatabaseColumn(null), nullValue());
  }

  @Test(expected = NullPointerException.class)
  public void convertNullDbValue() throws Exception {
    new CommonStateConverter().convertToEntityAttribute(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void convertUnsupportedDbValue() throws Exception {
    new CommonStateConverter().convertToEntityAttribute(9999);
  }
}