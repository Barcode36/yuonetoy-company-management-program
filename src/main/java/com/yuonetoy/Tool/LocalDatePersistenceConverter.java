package com.yuonetoy.Tool;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/*PA 2.1 부터 Attribute Converter라는 기능이 도입되었다. AttributeConverter 클래스를 상속받는 자체 Converter를 만들면,
Java8의 시간 데이터 타입을 JPA에서 인식할 수 있는 타입으로 자동으로 변환되게 할 수 있다. 아래는 java.time.LocalDate에 대한 Converter다.
자체 Converter를 만든다고 끝은 아니다. 어느 데이터에 이 Converter를 적용할지 지정해줘야 한다.
아래와 같이 Converter에 의한 자동변환이 필요한 데이터에 @Convert 애노테이션을 지정해준다.
 */
@Converter(autoApply = true)
public class LocalDatePersistenceConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate entityValue) {
        return (entityValue == null ? null : Date.from(entityValue.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date databaseValue) {
        return (databaseValue == null ? null : Instant.ofEpochMilli(databaseValue.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
    }
}