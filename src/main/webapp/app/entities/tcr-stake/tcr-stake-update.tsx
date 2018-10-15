import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './tcr-stake.reducer';
import { ITcrStake } from 'app/shared/model/tcr-stake.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITcrStakeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITcrStakeUpdateState {
  isNew: boolean;
}

export class TcrStakeUpdate extends React.Component<ITcrStakeUpdateProps, ITcrStakeUpdateState> {
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
      const { tcrStakeEntity } = this.props;
      const entity = {
        ...tcrStakeEntity,
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
    this.props.history.push('/entity/tcr-stake');
  };

  render() {
    const { tcrStakeEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="tcrserverApp.tcrStake.home.createOrEditLabel">Create or edit a TcrStake</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : tcrStakeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="tcr-stake-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="amountLabel" for="amount">
                    Amount
                  </Label>
                  <AvField id="tcr-stake-amount" type="string" className="form-control" name="amount" />
                </AvGroup>
                <AvGroup>
                  <Label id="tokenLabel" for="token">
                    Token
                  </Label>
                  <AvField id="tcr-stake-token" type="text" name="token" />
                </AvGroup>
                <AvGroup>
                  <Label id="sharesIssuedLabel" for="sharesIssued">
                    Shares Issued
                  </Label>
                  <AvField id="tcr-stake-sharesIssued" type="string" className="form-control" name="sharesIssued" />
                </AvGroup>
                <AvGroup>
                  <Label id="ownerIdentityLabel" for="ownerIdentity">
                    Owner Identity
                  </Label>
                  <AvField id="tcr-stake-ownerIdentity" type="text" name="ownerIdentity" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/tcr-stake" replace color="info">
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
  tcrStakeEntity: storeState.tcrStake.entity,
  loading: storeState.tcrStake.loading,
  updating: storeState.tcrStake.updating
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
)(TcrStakeUpdate);
