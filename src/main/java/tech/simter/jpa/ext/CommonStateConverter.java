package tech.simter.jpa.ext;

import tech.simter.persistence.CommonState;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * A {@link CommonState} {@link AttributeConverter} implementation that used to convert
 * entity attribute state into database column representation and back again.
 *
 * @author dragon 2017-04-28
 */
@Converter(autoApply = true)
public class CommonStateConverter extends PersistenceEnumConverter<CommonState, Integer> {
  @Override
  public CommonState convertToEntityAttribute(Integer dbData) {
    return CommonState.valueOf(dbData);
  }
}