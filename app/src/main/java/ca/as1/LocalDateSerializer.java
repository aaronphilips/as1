package ca.as1;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;

/**
 *
 * STRAIGHT UP COPIED FROM http://stackoverflow.com/questions/26411379/jodatime-localdate-localtime-not-parsing-with-custom-json-serializer-classes
 * Wanted to used Joda LocalDate, but needed to parse through gson builder
 */

public class LocalDateSerializer implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate>
{
    private static final DateTimeFormatter DATE_FORMAT = ISODateTimeFormat.date();


    public LocalDate deserialize(final JsonElement je, final Type type,
                                 final JsonDeserializationContext jdc) throws JsonParseException
    {
        final String dateAsString = je.getAsString();
        if (dateAsString.length() == 0)
        {
            return null;
        }
        else
        {
            return DATE_FORMAT.parseLocalDate(dateAsString);
        }
    }


    public JsonElement serialize(final LocalDate src, final Type typeOfSrc,
                                 final JsonSerializationContext context)
    {
        String retVal;
        if (src == null)
        {
            retVal = "";
        }
        else
        {
            retVal = DATE_FORMAT.print(src);
        }
        return new JsonPrimitive(retVal);
    }
}

