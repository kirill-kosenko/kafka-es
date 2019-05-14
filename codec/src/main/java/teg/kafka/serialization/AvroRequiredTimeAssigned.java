/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package teg.kafka.serialization;

import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class AvroRequiredTimeAssigned extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -734677867178272368L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AvroRequiredTimeAssigned\",\"namespace\":\"kafka.serialization\",\"fields\":[{\"name\":\"itemId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"requiredTime\",\"type\":\"int\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.String itemId;
  @Deprecated public int requiredTime;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public AvroRequiredTimeAssigned() {}

  /**
   * All-args constructor.
   * @param itemId The new value for itemId
   * @param requiredTime The new value for requiredTime
   */
  public AvroRequiredTimeAssigned(java.lang.String itemId, java.lang.Integer requiredTime) {
    this.itemId = itemId;
    this.requiredTime = requiredTime;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return itemId;
    case 1: return requiredTime;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: itemId = (java.lang.String)value$; break;
    case 1: requiredTime = (java.lang.Integer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'itemId' field.
   * @return The value of the 'itemId' field.
   */
  public java.lang.String getItemId() {
    return itemId;
  }

  /**
   * Sets the value of the 'itemId' field.
   * @param value the value to set.
   */
  public void setItemId(java.lang.String value) {
    this.itemId = value;
  }

  /**
   * Gets the value of the 'requiredTime' field.
   * @return The value of the 'requiredTime' field.
   */
  public java.lang.Integer getRequiredTime() {
    return requiredTime;
  }

  /**
   * Sets the value of the 'requiredTime' field.
   * @param value the value to set.
   */
  public void setRequiredTime(java.lang.Integer value) {
    this.requiredTime = value;
  }

  /**
   * Creates a new AvroRequiredTimeAssigned RecordBuilder.
   * @return A new AvroRequiredTimeAssigned RecordBuilder
   */
  public static AvroRequiredTimeAssigned.Builder newBuilder() {
    return new AvroRequiredTimeAssigned.Builder();
  }

  /**
   * Creates a new AvroRequiredTimeAssigned RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new AvroRequiredTimeAssigned RecordBuilder
   */
  public static AvroRequiredTimeAssigned.Builder newBuilder(AvroRequiredTimeAssigned.Builder other) {
    return new AvroRequiredTimeAssigned.Builder(other);
  }

  /**
   * Creates a new AvroRequiredTimeAssigned RecordBuilder by copying an existing AvroRequiredTimeAssigned instance.
   * @param other The existing instance to copy.
   * @return A new AvroRequiredTimeAssigned RecordBuilder
   */
  public static AvroRequiredTimeAssigned.Builder newBuilder(AvroRequiredTimeAssigned other) {
    return new AvroRequiredTimeAssigned.Builder(other);
  }

  /**
   * RecordBuilder for AvroRequiredTimeAssigned instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<AvroRequiredTimeAssigned>
    implements org.apache.avro.data.RecordBuilder<AvroRequiredTimeAssigned> {

    private java.lang.String itemId;
    private int requiredTime;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(AvroRequiredTimeAssigned.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.itemId)) {
        this.itemId = data().deepCopy(fields()[0].schema(), other.itemId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.requiredTime)) {
        this.requiredTime = data().deepCopy(fields()[1].schema(), other.requiredTime);
        fieldSetFlags()[1] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing AvroRequiredTimeAssigned instance
     * @param other The existing instance to copy.
     */
    private Builder(AvroRequiredTimeAssigned other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.itemId)) {
        this.itemId = data().deepCopy(fields()[0].schema(), other.itemId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.requiredTime)) {
        this.requiredTime = data().deepCopy(fields()[1].schema(), other.requiredTime);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'itemId' field.
      * @return The value.
      */
    public java.lang.String getItemId() {
      return itemId;
    }

    /**
      * Sets the value of the 'itemId' field.
      * @param value The value of 'itemId'.
      * @return This builder.
      */
    public AvroRequiredTimeAssigned.Builder setItemId(java.lang.String value) {
      validate(fields()[0], value);
      this.itemId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'itemId' field has been set.
      * @return True if the 'itemId' field has been set, false otherwise.
      */
    public boolean hasItemId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'itemId' field.
      * @return This builder.
      */
    public AvroRequiredTimeAssigned.Builder clearItemId() {
      itemId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'requiredTime' field.
      * @return The value.
      */
    public java.lang.Integer getRequiredTime() {
      return requiredTime;
    }

    /**
      * Sets the value of the 'requiredTime' field.
      * @param value The value of 'requiredTime'.
      * @return This builder.
      */
    public AvroRequiredTimeAssigned.Builder setRequiredTime(int value) {
      validate(fields()[1], value);
      this.requiredTime = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'requiredTime' field has been set.
      * @return True if the 'requiredTime' field has been set, false otherwise.
      */
    public boolean hasRequiredTime() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'requiredTime' field.
      * @return This builder.
      */
    public AvroRequiredTimeAssigned.Builder clearRequiredTime() {
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public AvroRequiredTimeAssigned build() {
      try {
        AvroRequiredTimeAssigned record = new AvroRequiredTimeAssigned();
        record.itemId = fieldSetFlags()[0] ? this.itemId : (java.lang.String) defaultValue(fields()[0]);
        record.requiredTime = fieldSetFlags()[1] ? this.requiredTime : (java.lang.Integer) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  private static final org.apache.avro.io.DatumWriter
    WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  private static final org.apache.avro.io.DatumReader
    READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
