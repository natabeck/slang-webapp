package io.cloudslang.web.entities;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/2/15
 * Time: 2:17 PM
 */
import io.cloudslang.engine.data.AbstractIdentifiable;
import io.cloudslang.score.facade.execution.ExecutionStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


@Entity
@Table(name = "EXECUTION_SUMMARY")
public class ExecutionSummaryEntity extends AbstractIdentifiable {

    @Column(name = "EXECUTION_ID", nullable = false)
    private Long executionId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private ExecutionStatus status;

    @Column(name = "RESULT")
    private String result;

    @Column(name = "OUTPUTS")
    private String outputs;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getExecutionId() {
        return executionId;
    }

    public void setExecutionId(Long executionId) {
        this.executionId = executionId;
    }


    public ExecutionStatus getStatus() {
        return status;
    }

    public void setStatus(ExecutionStatus status) {
        this.status = status;
    }

    public String getOutputs() {
        return outputs;
    }

    public void setOutputs(String outputs) {
        this.outputs = outputs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExecutionSummaryEntity that = (ExecutionSummaryEntity) o;

        if (executionId != null ? !executionId.equals(that.executionId) : that.executionId != null) return false;
        if (outputs != null ? !outputs.equals(that.outputs) : that.outputs != null) return false;
        if (result != null ? !result.equals(that.result) : that.result != null) return false;
        //noinspection RedundantIfStatement
        if (status != that.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = executionId != null ? executionId.hashCode() : 0;
        result1 = 31 * result1 + (status != null ? status.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (outputs != null ? outputs.hashCode() : 0);
        return result1;
    }
}


