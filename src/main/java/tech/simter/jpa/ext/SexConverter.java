package tech.simter.jpa.ext;

import tech.simter.persistence.Sex;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * A {@link Sex} {@link AttributeConverter} implementation that used to convert
 * entity attribute state into database column representation and back again.
 *
 * @author dragon 2017-04-28
 */
@Converter(autoApply = true)
public class SexConverter extends PersistenceEnumConverter<Sex, Integer> {
  @Override
  public Sex convertToEntityAttribute(Integer dbData) {
    return Sex.valueOf(dbData);
  }
}