package com.swayam.demo.springbootdemo.kafkadto;

public class CompletionSignal {

    private final Class<?> targetDto;
    private final String correlationId;

    public CompletionSignal() {
	this(null, null);
    }

    public CompletionSignal(Class<?> targetDto, String correlationId) {
	this.targetDto = targetDto;
	this.correlationId = correlationId;
    }

    public Class<?> getTargetDto() {
	return targetDto;
    }

    public String getCorrelationId() {
	return correlationId;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((correlationId == null) ? 0 : correlationId.hashCode());
	result = prime * result + ((targetDto == null) ? 0 : targetDto.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	CompletionSignal other = (CompletionSignal) obj;
	if (correlationId == null) {
	    if (other.correlationId != null)
		return false;
	} else if (!correlationId.equals(other.correlationId))
	    return false;
	if (targetDto == null) {
	    if (other.targetDto != null)
		return false;
	} else if (!targetDto.equals(other.targetDto))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "CompletionSignal [targetDto=" + targetDto + ", correlationId=" + correlationId
		+ "]";
    }

}
