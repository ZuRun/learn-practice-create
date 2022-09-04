package cn.zull.lpc.practice.test;

import cn.zull.lpc.common.basis.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author zurun
 * @date 2022/8/11 23:19:03
 */
public class NioTest {
    private static String jsonString = "{\"sceneSnapshot\":{\"timestamp\":\"1640805179617722112\",\"vehicle10ms\":{\"StrSys\":{\"StrAgFailSts\":0,\"StrAgCalSts\":0,\"StrWhlAgSAE\":1.3000001,\"StrWhlAgSpdSAE\":0,\"PnnAgVld\":0,\"PnnAgSAE\":-0.1,\"PnnAgOffsetSAE\":1.6,\"EstRackFrcVld\":0,\"EstRackFrcSae\":0,\"MtrTqVld\":0,\"MtrTqSAE\":0,\"TorsBarTqVld\":0,\"TorsBarTqSAE\":-0.26,\"EPSSts\":3,\"DrvngMod\":1,\"RampSts\":false,\"ACIMtrTqVld\":0,\"ACIMtrTqSAE\":0,\"Temperature\":0,\"SupInfo\":1,\"VMCHVIAvl\":0,\"EPSReqTyp\":0,\"BCUCTIAvl\":false,\"WarnLampReq\":0,\"SteerWhlAgAndSpdValid\":false,\"SWCFailSts\":0,\"SteerColTiltCtrlSts\":0,\"SteerColTiltPos\":5,\"SteerColTeleCtrlSts\":0,\"SteerColTelescopePos\":4,\"HODErrSts\":false,\"HOSts\":15,\"OverRideDetn\":0,\"timestampPtpNs\":[\"1640805179589754741\",\"1640805179602150301\",\"1640805179586625843\",\"1640805179586853874\",\"1640805179603009406\",\"1640805179562839578\",\"1640805179582113327\",\"1640805179563456457\",\"1640805179535035484\"]},\"BrkSys\":{\"BrkPdl\":{\"TrvlCalSts\":1,\"Trvl\":0,\"BrkPedlSts\":0,\"BrkpedlOvrd\":false,\"timestampPtpNs\":\"1640805179587811700\"},\"BrkPrsInfo\":{\"BrkPrsVld\":0,\"BrkPrs\":-0.5,\"BrkPrsOffsetVld\":0,\"BrkPrsOffset\":0,\"timestampPtpNs\":\"1640805179597760412\"},\"PrkBrk\":{\"EPBSwtSts\":0,\"EPBSts\":1,\"EPBMod\":0,\"CDPReq\":false,\"timestampPtpNs\":\"1640805179599920368\"},\"BrkOverHeat\":0,\"BrkHAZReq\":0,\"BCUBrkLiReq\":0,\"StstSts\":5,\"NoBrkF\":0,\"SupInfo\":2,\"BrkFldLvl\":0,\"BrkPadWearSts\":0,\"BrkFunSt\":{\"BDWActv\":false,\"ABAAvl\":true,\"ABAActv\":false,\"ABPAvl\":true,\"ABPActv\":false,\"ABSActv\":false,\"AVHSts\":1,\"DTCActv\":false,\"DWTActv\":false,\"EBAAvl\":true,\"EBAActv\":false,\"EBDActv\":false,\"HBAActv\":false,\"HDCSts\":0,\"HHCAvl\":true,\"HHCActv\":false,\"TCSActv\":false,\"TCSDeactv\":false,\"VDCActv\":false,\"VDCDeactv\":false,\"EBDFailLampReq\":false,\"VDCTCSLampInfo\":false,\"VDCTCSFailLampReq\":false,\"ABSFailLampReq\":false,\"VDCTCSOnOfflampReq\":false,\"AWBActv\":false,\"AWBAvl\":true,\"EBPActv\":false,\"HBCActv\":false,\"ARPCfgSts\":0,\"CDPActv\":false,\"CDPAvail\":true,\"ARPActv\":false,\"DTCAvl\":true,\"timestampPtpNs\":\"1640805179597978487\"},\"ExtBrkActv\":false,\"ExtBrkCp\":20199.9,\"ExtBrkSts\":2,\"MbRegenFrntAxleTar\":0,\"MbRegenFrntAxleTarQ\":1,\"MbRegenReAxleTar\":0,\"MbRegenReAxleTarQ\":1,\"StabyMagin\":0,\"StabyMaginQ\":0,\"BrkFldWarnReq\":0,\"BrkPadWearWarnReq\":0},\"PT\":{\"AccrPedal\":{\"EfcPosnVld\":0,\"EfcPosn\":0,\"ActPosnVld\":0,\"ActPosn\":0,\"PedlOvrd\":false,\"timestampPtpNs\":\"1640805179603252591\"},\"Gear\":{\"SlctrPosnVld\":0,\"ActGearVld\":0,\"TrgtGearVld\":0,\"SlctrPosn\":3,\"ActGear\":3,\"TrgtGear\":3,\"timestampPtpNs\":\"1640805179603997899\"},\"Motor\":[{\"IntdMotTqVld\":0,\"IntdMotTq\":0,\"ActMotTqVld\":0,\"ActMotTq\":0,\"MotSpdVld\":0,\"MotSpd\":0,\"SpdLimForFlt\":113,\"DampSts\":0,\"FailrSts\":1,\"HeatCap\":0,\"MotHoldSts\":0},{\"IntdMotTqVld\":0,\"IntdMotTq\":0,\"ActMotTqVld\":0,\"ActMotTq\":0,\"MotSpdVld\":0,\"MotSpd\":0,\"SpdLimForFlt\":255,\"DampSts\":0,\"FailrSts\":1,\"HeatCap\":0,\"MotHoldSts\":0,\"timestampPtpNs\":[\"1640805179576122668\",\"1640805179601859210\",\"1640805179603252591\"]}],\"VCURvsLampReq\":false,\"VCUBrkLampReq\":false,\"CruiseStatus\":1,\"VCUEPBReq\":1,\"CruiseStoredSpeed\":0,\"VCUPtWakeupReq\":false,\"DCChrgrPluginSts\":false,\"ACChrgrPluginSts\":false,\"VCUVehHldReq\":false,\"VCUBrkReqEna\":false,\"VCUForceFctEna\":false,\"VCUTarBrkFReq\":0},\"Whl\":{\"WhlDyn\":[{\"WhlSpdSts\":0,\"WhlSpdMovgDir\":0,\"WhlSpd\":0,\"WhlPlsCntrVld\":0,\"WhlPlsCntr\":0,\"WhlSpdTimestamp\":\"1640805179597087075\",\"timestampPtpNs\":\"1640805179597087075\"},{\"WhlSpdSts\":0,\"WhlSpdMovgDir\":0,\"WhlSpd\":0,\"WhlPlsCntrVld\":0,\"WhlPlsCntr\":0,\"WhlSpdTimestamp\":\"1640805179597087075\",\"timestampPtpNs\":\"1640805179597087075\"},{\"WhlSpdSts\":0,\"WhlSpdMovgDir\":0,\"WhlSpd\":0,\"WhlPlsCntrVld\":0,\"WhlPlsCntr\":0,\"WhlSpdTimestamp\":\"1640805179597298113\",\"timestampPtpNs\":\"1640805179597298113\"},{\"WhlSpdSts\":0,\"WhlSpdMovgDir\":0,\"WhlSpd\":0,\"WhlPlsCntrVld\":0,\"WhlPlsCntr\":0,\"WhlSpdTimestamp\":\"1640805179597298113\",\"timestampPtpNs\":\"1640805179597298113\"}],\"WHlTpms\":[{\"Press\":269.108,\"Temp\":26,\"SnsrFailSts\":0,\"BatSts\":0,\"PressSts\":0,\"DeltaPressSts\":0,\"TempSts\":0},{\"Press\":260.87,\"Temp\":25,\"SnsrFailSts\":0,\"BatSts\":0,\"PressSts\":0,\"DeltaPressSts\":0,\"TempSts\":0},{\"Press\":263.616,\"Temp\":23,\"SnsrFailSts\":0,\"BatSts\":0,\"PressSts\":0,\"DeltaPressSts\":0,\"TempSts\":0},{\"Press\":259.497,\"Temp\":23,\"SnsrFailSts\":0,\"BatSts\":0,\"PressSts\":0,\"DeltaPressSts\":0,\"TempSts\":0}],\"WhlDynTimestamp\":\"1640805179597298113\",\"timestampPtpNs\":\"1640805179597298113\"},\"VehDyn\":{\"VehSpd\":{\"VehSpdSts\":0,\"VehMovgDir\":0,\"VehSpdkph\":0,\"VehSpdmps\":0,\"VehFiltLngAcc\":0,\"VehSpdASILDSts\":0,\"VehSpdASILD\":0,\"VehDispSpd\":0,\"VCUVehDispSpdSts\":0,\"timestampPtpNs\":[\"1640805179597760412\",\"1640805179602150301\",\"1640805179505500305\"]},\"AxAyYrsCalSts\":1,\"LgtASts\":0,\"LgtSAEAg\":0.003000095,\"LgtSAEAmpss\":0.029430931,\"LatASts\":0,\"LatSAEAg\":0.001999905,\"LatSAEAmpss\":0.019619068,\"YawRateSts\":0,\"YawRateSAERps\":-0.00048869196,\"YawRateSAEDps\":-0.028000012,\"VehOdom\":1253,\"YawRateTimestamp\":\"1640805179594287609\",\"timestampPtpNs\":\"1640805179594287609\"},\"VehCtrlIf\":{\"LngCtrlIf\":{\"VLCAvl\":true,\"VLCActv\":false,\"VLCTarDecel\":1.937151e-7,\"LLCFctSt\":4,\"LLCIntrrptErrTyp\":5,\"AutoBrkgAvl\":false,\"AutoBrkgActv\":false,\"ADTSts\":1,\"HldLampReq\":0,\"FCC1ForceFctEna\":false,\"FCC1BrkReqEna\":false,\"FCC1TarBrkFReq\":0,\"FCC1VehHldReq\":false,\"FCC1VLCActv\":false,\"FCC1VLCAvl\":false,\"RVMCLgtAccCp\":0,\"RVMCLgtDecCp\":-1.27,\"RVMCLgtSts\":260,\"VCUCruiseCtrlMod\":0,\"VMCBrkOvrd\":0,\"VMCLgtAccCp\":0.37,\"VMCLgtDecCp\":-1.0699999,\"VMCLgtDecCpRdnt\":-0.32,\"VMCLgtSts\":0,\"timestampPtpNs\":[\"1640805179604219373\",\"1640805179597978487\",\"1640805179600380748\",\"1640805179604453281\",\"1640805179603997899\"]},\"LatCtrlIf\":{\"ActvExtIf\":0,\"HIAvl\":false,\"TOIAvl\":false,\"DAIAvl\":false,\"PAIAvl\":false,\"timestampPtpNs\":\"1640805179586625843\"}},\"publishPtpTs\":\"1640805179608368896\",\"publisherId\":\"d226c250-bbea-4d3c-82ca-69ef6f92e335\",\"counter\":\"1105305\",\"publishTs\":\"1640805179706533760\"},\"Actuator\":{\"LngLwSpdCtrl\":{\"FctSeln\":0,\"CntrlModReq\":0,\"LLCSt\":0,\"TarGearReq\":0,\"TarSpdKph\":0,\"StopDst\":0,\"ShutdownModReq\":0,\"EPBReq\":0,\"VehDstToVSlot\":0,\"SlopeDstOffset\":0},\"LatCtrl\":{\"ACIReqVld\":0,\"ACIReqSAE\":0,\"ACISafLimAngDyn\":0,\"ACISafLimAngRM\":0,\"ACISafLimAngDynOffs\":0,\"ACISafLimRate\":0,\"ACIRampRate\":0,\"ACISafLimAngLM\":0,\"ACISafLimMode\":0,\"CTIReqVal\":0,\"CTIReqSAE\":0,\"StrIFReq\":0,\"HVIReq\":0,\"HVIReqVld\":0,\"ACITsuSup\":0,\"ACIOvrdThr\":0},\"LngCtrlEmrgnc\":{\"AEBDecelReq\":false,\"AEBTarDecel\":0,\"EBADecelReq\":false,\"ABPReq\":false,\"AWBSnvtyLvl\":0,\"AWBReq\":false,\"ABASnvtyLvl\":0,\"ABAReq\":false},\"LngCtrlCmfrt\":{\"VLCReqFct\":0,\"VLCMode\":0,\"VLCTarA\":0,\"VLCMinJerkA\":0,\"VLCMaxJerkA\":0,\"VLCDrvOffReq\":false,\"VLCDecToStopReq\":false,\"VLCShutdownModReq\":0},\"VehModMng\":{\"ADDrvTypReq\":0,\"DDMReq\":0},\"SafetyState\":{\"SafetyState1\":0}},\"DrvrIF\":{\"SpdDispAndCfg\":{\"SASSLIFState\":0,\"SASSLWFWarnTrigger\":0,\"SASSpeedLimitAttribute\":0,\"SASSpeedLimitTakeover\":0,\"SASSpeedLimitUnit\":false,\"SASSpeedLimitValue\":0,\"SASSupSignAttribute\":false,\"SASSupSignType\":0,\"SASLocalHzrd\":0,\"SASRdFtrWarnSign\":0,\"SASTrffcLghtSts\":0},\"SdowBSD\":{\"SDOWOnOffSts\":false,\"LCAHapticOnOffSts\":false,\"BSDLCAOnOffSts\":false,\"SDOWWarnSts\":[0,3],\"ExtReqRADRR\":0,\"SDOWWarnReq\":[0],\"BSDLCASts\":[0,3],\"BSDLCAReWarnReq\":[0],\"FrntRSDSDisp\":[0,7],\"ReRiRSDSDisp\":7},\"CTAWarn\":{\"FCTAOnOffSts\":0,\"RCTAOnOffSts\":false,\"FCTASts\":0,\"RCTABrkSts\":1,\"CTAFrntWarnReq\":[0],\"CTAReWarnReq\":[0],\"RCTAWarnSts\":[0],\"FCTAWarnSts\":[0]},\"BodyCtrl\":{\"LightIf\":{\"HMAHiBeamReq\":false,\"HMASts\":1,\"MirrLightReq\":[0]},\"DoorUnlckReq\":0,\"RemLogSts\":0,\"ECOPlusModReqSts\":false},\"LngWarnSafe\":{\"FCWSetSts\":1,\"AEBSts\":3,\"PcwWarnReq\":0},\"NpDrvIF\":{\"Textinfo\":6,\"GoNotifyReq\":0,\"LnAssiTyp\":2,\"LnAssiSnvty\":2,\"LnAstHptcOnOffSts\":true,\"LnAssistSts\":0,\"DANADSts\":1,\"AdasLeLine\":0,\"AdasRiLine\":0,\"TurnLightReq\":0,\"TauGapSet\":0,\"DisplaySetSpeedSwtch\":false,\"SetSpeedUnit\":false,\"DisplaySetSpd\":0,\"DANADWTI\":0,\"ALCSsts\":0,\"LatCtrlTarLe\":0,\"LatCtrlTarRi\":0,\"LongCtrlTar\":false,\"TauGapChangeDisp\":0,\"LaneLines\":0,\"HODsymbol\":0,\"ELKSts\":3,\"ESFWarningSts\":0,\"EASSts\":0,\"EASWarningLevel\":0},\"ADSysSts\":{\"ADCUSSMode\":0},\"DMSIF\":{\"DrowsinessLevel\":0,\"DrowsinessSts\":0,\"DistractionLevel\":0,\"DistractionSts\":0},\"ADIcsIF\":{\"ShowSetCHA\":0,\"ShowSetDAALCS\":true,\"ShowSet360AP\":true,\"ADCVehDispWarn\":false},\"FuncEDR\":{\"LnAssistStsEDR\":0,\"LnAssiTypEDR\":0,\"AdasLeLineEDR\":0,\"ACSFB1StsEDR\":0,\"AdasRiLineEDR\":0,\"ELKStsEDR\":0,\"ACSFDStsEDR\":0,\"ACSFB2StsEDR\":0,\"ACSFAStsEDR\":0,\"ACSFCStsEDR\":1,\"ACSFEStsEDR\":0,\"ACCStsEDR\":0,\"ESFWarningStsEDR\":0}},\"pvat\":{\"initState\":20,\"insState\":1,\"positionType\":2,\"latitude\":0,\"longitude\":0,\"height\":0,\"undulation\":0,\"velocityNorth\":0,\"velocityEast\":0,\"velocityUp\":0,\"roll\":-0.263671875,\"pitch\":0.186767578125,\"azimuth\":0,\"biasGyroX\":0.014373779296875,\"biasGyroY\":-0.01910400390625,\"biasGyroZ\":-0.020294189453125,\"biasAccX\":0.0030517578125,\"biasAccY\":0,\"biasAccZ\":0.0244140625,\"stdLat\":2.9742742,\"stdLong\":3.254374,\"stdHeight\":2.9742742,\"stdVelNorth\":0.73344696,\"stdVelEast\":0.73344696,\"stdVelUp\":0.73344696,\"stdRoll\":0.73344696,\"stdPitch\":0.73344696,\"stdAzimuth\":0.73344696,\"utcTime\":{\"year\":0,\"month\":0,\"day\":0,\"hour\":0,\"min\":0,\"sec\":0,\"msec\":0},\"itowTime\":0,\"gpsRefWeek\":0,\"numSatTrack\":0,\"numSatUsed\":0,\"numSatDfrq\":0,\"updateFlag\":0,\"propagateTime\":0,\"gdprStatus\":1,\"rsdImuBdyX\":0,\"rsdImuBdyY\":0,\"rsdImuBdyZ\":0,\"dynaCalState\":0,\"publishPtpTs\":\"1640805179612290048\",\"publisherId\":\"e5c861d0-33ff-4300-9721-2decf415032a\",\"counter\":\"2219034\",\"publishTs\":\"1640805179710453312\"},\"DrvIn\":{\"StrWhlSwtch\":{\"AdUpSwtSts\":[0,0,0,0,0],\"EnUpSwtSts\":[0,0,0,0,0]},\"AdFunCfg\":{\"AEBOnOffReq\":1,\"DASTactileOnOff\":0,\"DrvAlertSysOnOff\":0,\"FCTAOnOffCmd\":1,\"FCWSetReq\":1,\"LnAssistTctlOnOff\":1,\"LCAOnOff\":1,\"LCATctlWarnOnOff\":1,\"RCTAReq\":1,\"SetHMA\":1,\"SetLnAssiAidTyp\":2,\"SetLaneAssiSnvty\":2,\"LKAStrSprtLvlSet\":0,\"RCTABReq\":1,\"SAPAPrkgModReq\":1,\"CDCFailSts\":0,\"Set360AP\":1,\"SetDAALCS\":1,\"SetDASpeedAssist\":0,\"SetDASteerAssist\":1,\"SetSWF\":1,\"ELKOnOff\":0,\"SdowOnOffReq\":1,\"GoNotifierSnvty\":1,\"DrowsinessSwtSts\":0,\"ELKSwtSts\":1,\"DistractionSwtSts\":0,\"SetDANOP\":0,\"SetSpeedCtrlSts\":0,\"CurveSpeedAssist\":0,\"TowModActv\":0,\"GoNotifierOnOff\":1,\"AESOnOffSts\":false,\"SwtichDANOP\":false,\"SetDANOPALC\":1,\"ManHornSwtSts\":false},\"FogLiPushSwtSts\":0,\"FrntWiprInterSpd\":1,\"FrntWiprSwtSts\":0,\"HiBeamSwtSts\":0,\"TurnIndcrSwtSts\":0,\"WiprAutoSwtSts\":0,\"WshrReWiprSwtSts\":0,\"SCMFailSts\":0,\"FogLiSCMCmd\":0,\"HiBeamSCMCmd\":0,\"ReWiprSCMCmd\":0,\"SVCAvl\":0,\"NaviSpdLim\":0,\"WTIDispSt\":0,\"NaviSpdUnit\":0,\"NaviSpdLimSts\":0,\"NaviCurrentRoadTyp\":0,\"NavCtryCod\":0,\"CDCEqpmt\":{\"AUDIOsts\":false,\"HUDsts\":false,\"ICSsts\":false,\"ICsts\":false},\"DATauGapStored\":0,\"DMSInfo\":{\"DMSAlertLvl\":0,\"DMSAvlSts\":0,\"DMSDrvrAttention\":0,\"DMSDrvrAttentionConfidence\":0,\"DMSDrvrEyeGazeDir\":0,\"DMSDrvrEyeGazeDirConfidence\":0,\"DMSDrvrEyeSts\":0,\"DMSDrvrEyeStsConfidence\":0,\"DMSDrvrHdPoseDir\":0,\"DMSDrvrHdPoseDirConfidence\":0,\"DMSFailSafe\":0,\"DMSSts\":0,\"DMSDrvrFatigue\":0,\"DMSDrvrFatigueConfidence\":0},\"SVCInfo\":{\"SVCRiShaFb\":0,\"SVCReShaFb\":0,\"SVCLeShaFb\":0,\"SVCFrntShaFb\":0,\"F120ShaFb\":0,\"SVCLeShaReq\":0,\"SVCFrntShaReq\":0,\"SVCReShaReq\":0,\"F120ShaReq\":0,\"SVCRiShaReq\":0},\"VehEgyMngtMod\":0,\"WheelBrkPReLe\":0,\"WheelBrkPFrntRi\":0,\"WheelBrkPFrntLe\":0,\"WheelBrkPReRi\":0,\"BrkPedlTrvl\":0,\"PwrSwapSteerWhlWarn\":0,\"DriverPSAPRequest\":0,\"PSAPParkViewStatus\":0,\"VehPrepReq\":0,\"StoreDrvrSlotSelnReq\":0,\"LockConfig\":false,\"DriverSAPAReq\":0,\"DriverSAPASlotSelection\":0,\"NBSDrvrSts\":0},\"Ahc\":{\"AhcSysSt\":1,\"AhcHiBmReq\":false}},\"publishPtpTs\":0,\"publisherId\":\"\",\"counter\":0,\"publishTs\":0,\"metaData\":{\"date\":20211230,\"fileName\":\"snap_1640805178717\",\"utcTimeStamp\":1640805179718687000,\"bizType\":\"snapshot\",\"filePath\":\"/snap_1640805178717\",\"dataSize\":698988,\"adcVersion\":\"\",\"triggerTime\":1640794375284422100,\"uuid\":\"5d52eab1-812b-4710-af62-73e139050512\",\"dealTime\":1651125178745,\"createTime\":1650965140,\"vehicleId\":\"\",\"kafkaProduceTime\":\"2022-04-26 17:25:40\",\"dlbFileNameKey\":\"snap\",\"fileType\":\"snap_1640805178717\",\"vehicleUuid\":\"38860889-d227-43d7-bd38-63eb74db5fd2\"}}\n";

    public static void main(String[] args) {
        Deque<String> deque = new LinkedList<>();
        JSONObject map = new JSONObject();
        backtrack(deque, jsonString, map);
        System.out.println(map);
    }

    public static void backtrack(Deque<String> deque, String jsonString, JSONObject map) {
        if (StringUtils.isEmpty(jsonString)) return;


        StringBuilder builder = new StringBuilder();
        for (String k : deque) {
            if (builder.length() > 0) builder.append("_");
            builder.append(k);
        }
        String prefixKey = builder.toString();

        if (jsonString.startsWith("[") ||jsonString.startsWith("\\[") ) {
            // 数组
            map.put(prefixKey, jsonString);
            return;
        }
        if(!jsonString.startsWith("{")&&!jsonString.startsWith("\\{")) {
            // 其他类型
            map.put(prefixKey, jsonString);
            return;
        }
        JSONObject json = JSONObject.parseObject(jsonString);

        // 遍历key
        json.keySet().forEach(key -> {
            deque.offerLast(key);
            backtrack(deque, json.getString(key), map);
            deque.pollLast();
        });
    }
}
