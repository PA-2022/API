#!/bin/sh
cd /home/runner/.ssh/ && echo "${{ secrets.EC2_SSH_KEY }}" > rsa-key.pem && chmod 400 rsa-key.pem 
sudo nohup ssh -o StrictHostKeyChecking=no -tt -i "/home/runner/.ssh/rsa-key.pem" ec2-user@${{ secrets.HOST_DNS }} &
        
