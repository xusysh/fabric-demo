#!/bin/bash

function one_line_pem {
    echo "`awk 'NF {sub(/\\n/, ""); printf "%s\\\\\\\n",$0;}' $1`"
}

function json_ccp {
    local PP=$(one_line_pem $5)
    local CP=$(one_line_pem $6)
    sed -e "s/\${ORG}/$1/" \
        -e "s/\${P0PORT}/$2/" \
        -e "s/\${P1PORT}/$3/" \
        -e "s/\${CAPORT}/$4/" \
        -e "s#\${PEERPEM}#$PP#" \
        -e "s#\${CAPEM}#$CP#" \
        -e "s/\${IP}/$7/" \
        ccp-template.json 
}

function yaml_ccp {
    local PP=$(one_line_pem $5)
    local CP=$(one_line_pem $6)
    sed -e "s/\${ORG}/$1/" \
        -e "s/\${P0PORT}/$2/" \
        -e "s/\${P1PORT}/$3/" \
        -e "s/\${CAPORT}/$4/" \
        -e "s#\${PEERPEM}#$PP#" \
        -e "s#\${CAPEM}#$CP#" \
        -e "s/\${IP}/$7/" \
        ccp-template.yaml | sed -e $'s/\\\\n/\\\n        /g'
}

ORG=3
IP=47.116.140.61
P0PORT=7051
P1PORT=7051
CAPORT=7054
PEERPEM=../../crypto-config/peerOrganizations/org$ORG.ccb.com/tlsca/tlsca.org$ORG.ccb.com-cert.pem
CAPEM=../../crypto-config/peerOrganizations/org$ORG.ccb.com/ca/ca.org$ORG.ccb.com-cert.pem

echo "$(json_ccp $ORG $P0PORT $P1PORT $CAPORT $PEERPEM $CAPEM $IP)" > connection-Org$ORG.json
echo "$(yaml_ccp $ORG $P0PORT $P1PORT $CAPORT $PEERPEM $CAPEM $IP)" > connection-Org$ORG.yaml

cp ../../crypto-config/peerOrganizations/org$ORG.ccb.com/ca/ca.org$ORG.ccb.com-cert.pem ./