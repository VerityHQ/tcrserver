import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './tcr.reducer';
import { ITcr } from 'app/shared/model/tcr.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITcrUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITcrUpdateState {
  isNew: boolean;
}

export class TcrUpdate extends React.Component<ITcrUpdateProps, ITcrUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { tcrEntity } = this.props;
      const entity = {
        ...tcrEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/tcr');
  };

  render() {
    const { tcrEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="tcrserverApp.tcr.home.createOrEditLabel">Create or edit a Tcr</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : tcrEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="tcr-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="tcrHashLabel" for="tcrHash">
                    Tcr Hash
                  </Label>
                  <AvField id="tcr-tcrHash" type="text" name="tcrHash" />
                </AvGroup>
                <AvGroup>
                  <Label id="tcrNameLabel" for="tcrName">
                    Tcr Name
                  </Label>
                  <AvField id="tcr-tcrName" type="text" name="tcrName" />
                </AvGroup>
                <AvGroup>
                  <Label id="contentLabel" for="content">
                    Content
                  </Label>
                  <AvField id="tcr-content" type="text" name="content" />
                </AvGroup>
                <AvGroup>
                  <Label id="bondingCurveLabel" for="bondingCurve">
                    Bonding Curve
                  </Label>
                  <AvField id="tcr-bondingCurve" type="string" className="form-control" name="bondingCurve" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/tcr" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  tcrEntity: storeState.tcr.entity,
  loading: storeState.tcr.loading,
  updating: storeState.tcr.updating
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TcrUpdate);
