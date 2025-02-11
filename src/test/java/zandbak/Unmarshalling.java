package zandbak;

/*
 * Copyright 2008 Web Cohesion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.webcohesion.ofx4j.domain.data.ResponseEnvelope;
import com.webcohesion.ofx4j.io.AggregateUnmarshaller;
import com.webcohesion.ofx4j.io.OFXParseException;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Ryan Heaton
 * @author Scott Priddy
 */
public class Unmarshalling {

  private static final Log LOG = LogFactory.getLog(Unmarshalling.class);

  public static void main(String[] argv) {
    AggregateUnmarshaller<ResponseEnvelope> unmarshaller = new AggregateUnmarshaller<ResponseEnvelope>(
        ResponseEnvelope.class);

    try {
      unmarshaller.unmarshal(Unmarshalling.class.getResourceAsStream(
          "F:\\dev\\Tools\\OFXViewer\\resources\\NL34INGB0000187782_NL34INGB0000187782_20231001_20231009.ofx"));
    } catch (IOException | OFXParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println("End");
  }
}
