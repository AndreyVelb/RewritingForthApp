package com.example.first;

import com.example.exception.FulfillLogicException;
import com.example.first.model.dto.*;
import com.example.first.model.entity.DropIdInfo;
import com.example.first.model.entity.EXEDataObject;
import com.example.first.model.entity.XLaneRow;
import com.example.first.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class RFMoveFlowThroughQP1S1 {


    private final PickDetailRepositoryMock pickDetailRepositoryMock = new PickDetailRepositoryMock();
    private final XPickDetailRepositoryMock xPickDetailRepositoryMock = new XPickDetailRepositoryMock();
    private final TransshipRepositoryMock transshipRepositoryMock = new TransshipRepositoryMock();
    private final LaneRepositoryMock laneRepositoryMock = new LaneRepositoryMock();
    private final DropIdRepositoryMock dropIdRepositoryMock = new DropIdRepositoryMock();
    private final TaskDetailRepositoryMock taskDetailRepositoryMock = new TaskDetailRepositoryMock();

    private static final Logger logger = LoggerFactory.getLogger(RFMoveFlowThroughQP1S1.class);


    //Use this function to find out whether information passed in was from a dropid and what the other information was for that
    public EXEDataObject dropIDCheck() {

        int rowCount;

        List<DropIdInfo> dropIdObject;

        EXEDataObject result = EXEDataObject.builder()
                .toLoc(" ")
                .caseId(" ")
                .build();

        String INid;
        List<XLaneRow> lanes;
        int taskId = (int) (Math.random() * 10) + 5;

        CustomerAndDoorDto customerAndDoorDto = CustomerAndDoorDto.builder().build();

        int caseIdRowCount = 0;

        INid = "movableunit";                       //theCurrentDO.GetString('movableunit', INid);

        rowCount = dropIdRepositoryMock.getCountByDropId(INid);

        //see if it found the rows
        if (rowCount == 0) {
            //Didn't find a DropID, how about a non-shipped CaseID?
            caseIdRowCount = pickDetailRepositoryMock.getCountOfPickDetailByCaseIdAndStatus9(INid);

            if (caseIdRowCount == 0) {
                return EXEDataObject.builder()
                        .resultMessage("NODROPID")
                        .build();
            }
        }

        //Are we dealing with a CaseID?
        if (rowCount == 0 && caseIdRowCount > 0) {
            //If just one PICK for the CaseID (eg. pallet pick), then just retrieve the details

            if (caseIdRowCount == 1) {
                String loc = " ";

                Optional<PickDetailDto> optionalOfPickDetailDto = pickDetailRepositoryMock.getAllPickDetails(INid);
                if (optionalOfPickDetailDto.isPresent()) {
                    PickDetailDto pickDetailDto = optionalOfPickDetailDto.get();
                    updateResult(result, pickDetailDto);
                    customerAndDoorDto.setConsigneeKey(pickDetailDto.getConsigneeKey());
                    customerAndDoorDto.setHeaderDoor(pickDetailDto.getHeaderDoor());
                    loc = pickDetailDto.getLoc();
                }

                // If we haven't moved this Pick yet, set fromloc = PickLoc
                if (loc.isBlank()) {
                    result.setToLoc(loc);
                }
                result.setFromLoc(result.getToLoc());

                //If we have more than one PICK for the CaseID, we need to get a little more involved
            } else {

                MaxLocDto maxLocDto = pickDetailRepositoryMock.getMaxLocDto();

                if (maxLocDto.getMaxFromLoc().isEmpty()) {
                    maxLocDto.setMaxFromLoc(" ");
                }

                if (maxLocDto.getToLoc().isBlank()) {
                    maxLocDto.setToLoc(maxLocDto.getMaxFromLoc());
                }
                result.setToLoc(maxLocDto.getToLoc());
                result.setFromLoc(maxLocDto.getToLoc());

                // Retrieve Customer and Door fields from header - used later to find lane
                customerAndDoorDto = pickDetailRepositoryMock.getCustomerAndDoorDto(INid);
            }
        } else {
            //Okay, we are dealing with a DropID
            String status = dropIdRepositoryMock.getStatus(INid);

            if (status.equals("9")) {
                throw new FulfillLogicException("pSeverity = SP_ER_USER, " +
                        "pReasonCode = EF_LE_INVALIDDATA, " +
                        "pSetNumber = EF_MS_COMMON, " +
                        "pMsgNumber = 102, " +
                        "pDefaultMsg = NX('Drop %1 is shipped' ), " +
                        "pParam1 = TextData(Value=INid)");
                //return 'Container is Shipped';
            }

            //Start with the last used detail (ie. the 'ORDER BY').
            dropIdObject = dropIdRepositoryMock.getAllByDropIdOrderDesc(INid);

            boolean isFound = false;

            for (DropIdInfo item : dropIdObject) {
                switch (item.getIdType()) {
                    case "1" -> {
                        //Find out how many PICKDETAIL records for this CaseID.
                        //Would previously fail if a situation in which multiple picks for CaseID - ie. 1+ rows retrieved for a singleton SELECT.

                        caseIdRowCount = pickDetailRepositoryMock.getCountOfPickDetailByCaseIdAndStatus9(item.getChildId());

                        //If just one PICK for the CaseID (eg. pallet pick, non-cartonized pick), then just retrieve all the details.
                        if (caseIdRowCount == 1) {
                            String loc = " ";

                            Optional<PickDetailDto> optionalOfPickDetailDto = pickDetailRepositoryMock.getAllPickDetails(item.getChildId());
                            if (optionalOfPickDetailDto.isPresent()) {
                                PickDetailDto pickDetailDto = optionalOfPickDetailDto.get();
                                updateResult(result, pickDetailDto);
                                customerAndDoorDto.setConsigneeKey(pickDetailDto.getConsigneeKey());
                                customerAndDoorDto.setHeaderDoor(pickDetailDto.getHeaderDoor());
                                loc = pickDetailDto.getLoc();
                                isFound = true;
                            }

                            //If we haven't moved this Pick yet, set fromloc = PickLoc
                            if (item.getDropLoc().equals(" ") || item.getDropLoc().equals("")) {
                                result.setFromLoc(loc);
                            } else {
                                result.setFromLoc(item.getDropLoc());
                            }

                            //If we have more than one PICK for the CaseID, we need to get a little more involved
                        } else if (caseIdRowCount > 1) {
                            MaxLocDto maxLocDto = pickDetailRepositoryMock.getMaxLocDto();

                            if (maxLocDto.getMaxFromLoc() == null) maxLocDto.setMaxFromLoc(" ");

                            if (item.getDropLoc() != null) result.setFromLoc(maxLocDto.getMaxFromLoc());
                            else result.setFromLoc(item.getDropLoc());

                            //Retrieve Customer and Door fields from header - used later to find lane
                            customerAndDoorDto = pickDetailRepositoryMock.getCustomerAndDoorDto(item.getChildId());
                        }
                    }
                    case "2" -> {
                        Optional<XPickDetailDto> optionalOfxPickDetailDto = xPickDetailRepositoryMock.getAllXPickDetails(item.getChildId());
                        if (optionalOfxPickDetailDto.isPresent()) {
                            XPickDetailDto xPickDetailDto = optionalOfxPickDetailDto.get();
                            updateResult(result, xPickDetailDto);

                            customerAndDoorDto.setConsigneeKey(xPickDetailDto.getConsigneeKey());
                            isFound = true;
                        }
                        result.setFromLoc(item.getDropLoc());
                    }
                    case "3" -> {
                        Optional<TransshipDto> optionalOfTransshipDto = transshipRepositoryMock.getTransshipDetails(item.getChildId());
                        if (optionalOfTransshipDto.isPresent()) {
                            TransshipDto transshipDto = optionalOfTransshipDto.get();
                            result.setQty(transshipDto.getQty());
                            customerAndDoorDto.setConsigneeKey(transshipDto.getConsigneeKey());

                            isFound = true;
                        }
                        result.setFromLoc(item.getDropLoc());
                    }
                    case "4" -> {
                        //there should be no dropid's be
                    }
                }
                if (isFound) break;
            }

        }

        //Then get info for the dropid and find out any outbound lanes for a customer to find out where to move it to
        //gettting the outbound lanes

        lanes = laneRepositoryMock.getAllByConsigneeKey(customerAndDoorDto.getConsigneeKey());

        //If no Lane found, try and use the Door from the shipment order header.
        //Otherwise, fail as before.
        if (lanes.isEmpty()) {
            if (customerAndDoorDto.getHeaderDoor().equals("") || customerAndDoorDto.getHeaderDoor().equals(" ")) {
                logger.info("SP_MT_DEBUG, SP_ST_USER2, pGroup = EF_LG_RF, pLevel = 1 " +
                        "pSetNumber = EF_MS_RF, " +
                        "pMsgNumber = 200, " +
                        "pDefaultMsg = NX('Did not find Lane.'));");

                throw new FulfillLogicException("pSeverity = SP_ER_USER, " +
                        "pReasonCode = EF_LE_INVALIDDATA, " +
                        "pSetNumber = EF_MS_RF, " +
                        "pMsgNumber = 55, " +
                        "pDefaultMsg = NX('No Outbound Lanes Assigned')");

            } else {
                logger.info("SP_MT_DEBUG, SP_ST_USER2, pGroup = EF_LG_RF, pLevel = 255, " +
                        "pSetNumber = EF_MS_RF, " +
                        "pMsgNumber = 646, " +
                        "pDefaultMsg = NX('Did not find Lane - using door from shipment order.')");

                result.setToLoc(customerAndDoorDto.getHeaderDoor());     //Since we have no Lane, lets use the Door as the ToLoc
            }

        } else result.setToLoc(lanes.get(1).getLaneKey());   //Let's use the Lane as the ToLoc

        //  lane = (lanes[1].lanekey).value;

        //Pick and Drop
        //Find the pick and drop location between the current from location, and the final location (Lane).
        //Note: If FromLoc/ToLoc not a valid Location, then GetPickandDropLoc method returns pToLoc passed.
        result.setToLoc(getPickAndDropLoc(result.getFromLoc(), result.getToLoc()));

        result.setFromTag("0");
        result.setToTag("0");
        result.setResultMessage("Everything OK");
        return result;
    }

    public MoveInfoDto ProcessStep(String movableUnit) {

        //Get the next task id
        int taskId = (int) (Math.random() * 10 + 5);

        MoveInfoDto result = MoveInfoDto.builder()
                .movLogKey("MOVLOGKEY")
                .build();

        String userId;

        logger.info("SP_MT_DEBUG, SP_ST_USER2, pGroup = EF_LG_RF, pLevel = 255, " +
                "pSetNumber = EF_MS_RF, " +
                "pMsgNumber = 648, " +
                "pDefaultMsg = NX( 'Entering FlowThrough move query.')");

        //Защита от фиктивных размещений
//        X5CheckPainShip cps = new X5CheckPainShip();
//        cps.CheckPainShip(theContext, movableUnit);

        EXEDataObject exeDataObject = dropIDCheck();

        if (!exeDataObject.getResultMessage().equals("NODROPID")) {
            //theCurrentDo.getstring('userid', userid);
            userId = "1";
            logger.info(movableUnit + ", MV," + exeDataObject.getFromLoc() + ", " + exeDataObject.getToLoc() + ", " + userId + result.getMovLogKey());
            result.setToTag(exeDataObject.getToTag());
            result.setFromLoc(exeDataObject.getFromLoc());
            result.setToLoc(exeDataObject.getToLoc());
            result.setPackKey("");
            result.setRefNum("");

            return result;
        }

        Optional<TaskDetailDto> optionalOfTaskDetailDto = taskDetailRepositoryMock.getAllByTOIdStatus0AndTaskTypeMV(movableUnit);

        if (optionalOfTaskDetailDto.isEmpty()) {
            throw new FulfillLogicException("pSeverity = SP_ER_USER, " +
                    "pReasonCode = EF_LE_INVALIDDATA, " +
                    "pSetNumber = EF_MS_RF, " +
                    "pMsgNumber = 56, " +
                    "pDefaultMsg = NX('No Move Task' )");
        } else {
            logger.info("SP_MT_DEBUG, SP_ST_USER2, pGroup = EF_LG_RF, pLevel = 255, " +
                    "pSetNumber = EF_MS_RF, " +
                    "pMsgNumber = 650, " +
                    "pDefaultMsg = NX(Move Flow Through Found task.)");

            TaskDetailDto dto = optionalOfTaskDetailDto.get();

            result.setCaseId(dto.getCaseId());
            result.setStorer(dto.getStorerKey());
            result.setSku(dto.getSku());
            result.setDescr(dto.getDescr());
            result.setLotNum(dto.getLot());
            result.setFromTag(dto.getFromId());
            result.setToId(dto.getToId());
            result.setQty(dto.getQty());
            result.setUom(dto.getUom());
            result.setFromLoc(dto.getFromLoc());
            result.setToLoc(getPickAndDropLoc(result.getFromLoc(), result.getToLoc()));         //Get the Pick and Drop location for display
            result.setPackKey(" ");
            result.setRefNum(" ");
        }

        logger.info("movableunit, 'MV', fromloc, toloc, '', userid, movlogkey, ''");

        return result;
    }

    private void updateResult(EXEDataObject result, PickDetailDto dto) {
        result.setStorer(dto.getStorer());
        result.setSku(dto.getSku());
        result.setDescr(dto.getDESCR());
        result.setLotNum(dto.getLotnum());
        result.setId(dto.getId());
        result.setUom(dto.getUom());
        result.setQty(dto.getQty());
        result.setToLoc(dto.getToLoc());
    }

    private void updateResult(EXEDataObject result, XPickDetailDto dto) {
        result.setStorer(dto.getStorer());
        result.setSku(dto.getSku());
        result.setDescr(dto.getDESCR());
        result.setLotNum(dto.getLotnum());
        result.setId(dto.getId());
        result.setUom(dto.getUom());
        result.setQty(dto.getQty());
    }

    //A method that simulates a method call: SELF.GetPickAndDropLoc(FromLoc, ToLoc, theContext.theSQLMgr.thisSession)
    private String getPickAndDropLoc(String fromLoc, String toLoc) {
        return "Some message";
    }
}
